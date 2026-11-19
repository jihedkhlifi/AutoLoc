package tn.esprit.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolocapi.domain.Client;
import tn.esprit.autolocapi.domain.Contrat;
import tn.esprit.autolocapi.domain.Reservation;
import tn.esprit.autolocapi.domain.StatutReservation;
import tn.esprit.autolocapi.domain.Vehicule;
import tn.esprit.autolocapi.dto.ReservationInputDto;
import tn.esprit.autolocapi.dto.ReservationResponseDto;
import tn.esprit.autolocapi.dto.ReservationWithContratRequestDTO;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.mapper.ReservationMapper;
import tn.esprit.autolocapi.repository.IClientRepository;
import tn.esprit.autolocapi.repository.IContratRepository;
import tn.esprit.autolocapi.repository.IReservationRepository;
import tn.esprit.autolocapi.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private static final String RESSOURCE = "Reservation";

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final IContratRepository contratRepository;
    private final ReservationMapper mapper;

    @Override
    public ReservationResponseDto create(ReservationInputDto dto) {
        Reservation reservation = mapper.toEntity(dto);
        valider(reservation);
        return mapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    public ReservationResponseDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    private Reservation getEntity(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, id));
    }

    @Override
    public List<ReservationResponseDto> findAll() {
        return reservationRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ReservationResponseDto update(Long id, ReservationInputDto dto) {
        Reservation reservation = mapper.toEntity(dto);
        valider(reservation);
        Reservation existante = getEntity(id);
        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());
        if (reservation.getVehicule() != null) existante.setVehicule(reservation.getVehicule());
        if (reservation.getClient() != null) existante.setClient(reservation.getClient());
        return mapper.toDto(reservationRepository.save(existante));
    }

    @Override
    public void deleteById(Long id) {
        if (!reservationRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESSOURCE, id);
        }
        reservationRepository.deleteById(id);
    }

    // ------------------------------------------------------------------
    // Services d'affectation
    // ------------------------------------------------------------------

    /** Affecte la reservation idReservation au client identifie par son nom et son prenom. */
    @Override
    @Transactional
    public ReservationResponseDto affecterReservationAClient(Long idReservation, String nom, String prenom) {
        Reservation reservation = getEntity(idReservation);
        Client client = ResultatUnique.exiger(
                clientRepository.findByNomIgnoreCaseAndPrenomIgnoreCase(nom.trim(), prenom.trim()),
                "client nomme " + nom + " " + prenom);
        reservation.setClient(client);
        return mapper.toDto(reservationRepository.save(reservation));
    }

    /**
     * Cree une reservation puis le contrat qui lui est associe, dans une seule transaction :
     * si la creation du contrat echoue, la reservation n'est pas enregistree non plus.
     */
    @Override
    @Transactional
    public ReservationResponseDto creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO) {
        Vehicule vehicule = vehiculeRepository.findById(requestDTO.vehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", requestDTO.vehiculeId()));
        Client client = clientRepository.findById(requestDTO.clientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", requestDTO.clientId()));

        Reservation reservation = new Reservation();
        reservation.setDateDebut(requestDTO.dateDebut());
        reservation.setDateFin(requestDTO.dateFin());
        reservation.setStatut(requestDTO.statut() != null ? requestDTO.statut() : StatutReservation.CONFIRMEE);
        reservation.setVehicule(vehicule);
        reservation.setClient(client);
        valider(reservation);
        reservation = reservationRepository.save(reservation);

        Contrat contrat = new Contrat();
        contrat.setDateSignature(requestDTO.dateSignature());
        contrat.setMontantTotal(requestDTO.montantTotal() != null
                ? requestDTO.montantTotal()
                : calculerMontant(vehicule, requestDTO.dateDebut(), requestDTO.dateFin()));
        contrat.setValide(requestDTO.valide());
        contrat.setReservation(reservation);
        contrat = contratRepository.save(contrat);

        // cote inverse de la relation 1-1 : mis a jour pour que la reponse contienne contratId
        reservation.setContrat(contrat);
        return mapper.toDto(reservation);
    }

    /** Montant = tarif journalier x nombre de jours (1 jour minimum). */
    private static BigDecimal calculerMontant(Vehicule vehicule, LocalDate debut, LocalDate fin) {
        if (vehicule.getTarifJournalier() == null) {
            return null;
        }
        long jours = Math.max(1, ChronoUnit.DAYS.between(debut, fin));
        return vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(jours));
    }

    private void valider(Reservation reservation) {
        if (reservation.getDateDebut() != null && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
