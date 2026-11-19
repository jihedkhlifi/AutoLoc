package tn.esprit.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolocapi.domain.Contrat;
import tn.esprit.autolocapi.domain.Reservation;
import tn.esprit.autolocapi.dto.ContratInputDto;
import tn.esprit.autolocapi.dto.ContratResponseDto;
import tn.esprit.autolocapi.exception.ConflitException;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.mapper.ContratMapper;
import tn.esprit.autolocapi.repository.IContratRepository;
import tn.esprit.autolocapi.repository.IReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements IContratService {

    private static final String RESSOURCE = "Contrat";

    private final IContratRepository contratRepository;
    private final IReservationRepository reservationRepository;
    private final ContratMapper mapper;

    @Override
    public ContratResponseDto create(ContratInputDto dto) {
        Contrat contrat = mapper.toEntity(dto);
        valider(contrat);
        // Lien proprietaire JPA indispensable pour la cascade Contrat -> Paiements.
        if (contrat.getPaiements() != null) {
            contrat.getPaiements().forEach(paiement -> paiement.setContrat(contrat));
        }
        return mapper.toDto(contratRepository.save(contrat));
    }

    @Override
    public ContratResponseDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    private Contrat getEntity(Long id) {
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, id));
    }

    @Override
    public List<ContratResponseDto> findAll() {
        return contratRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public ContratResponseDto update(Long id, ContratInputDto dto) {
        Contrat contrat = mapper.toEntity(dto);
        valider(contrat);
        Contrat existant = getEntity(id);
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());
        // ni l'identifiant ni la collection de paiements (orphanRemoval) ne sont touchés
        if (contrat.getReservation() != null) existant.setReservation(contrat.getReservation());
        return mapper.toDto(contratRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESSOURCE, id);
        }
        // cascade = ALL + orphanRemoval sur Contrat.paiements : les paiements sont supprimés avec le contrat
        contratRepository.deleteById(id);
    }

    // ------------------------------------------------------------------
    // Service d'affectation
    // ------------------------------------------------------------------

    /**
     * Affecte le contrat signe a la date dateSignature a la reservation qui va de dateDebut a dateFin.
     * La relation Contrat - Reservation est 1-1 : chacun ne peut etre lie qu'a un seul autre.
     */
    @Override
    @Transactional
    public ContratResponseDto affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin) {
        if (dateFin.isBefore(dateDebut)) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
        Contrat contrat = ResultatUnique.exiger(
                contratRepository.findByDateSignature(dateSignature),
                "contrat signe le " + dateSignature);
        Reservation reservation = ResultatUnique.exiger(
                reservationRepository.findByDateDebutAndDateFin(dateDebut, dateFin),
                "reservation du " + dateDebut + " au " + dateFin);

        if (reservation.getContrat() != null && !reservation.getContrat().getIdContrat().equals(contrat.getIdContrat())) {
            throw new ConflitException("La reservation " + reservation.getIdReservation()
                    + " possede deja le contrat " + reservation.getContrat().getIdContrat());
        }
        if (contrat.getReservation() != null
                && !contrat.getReservation().getIdReservation().equals(reservation.getIdReservation())) {
            throw new ConflitException("Le contrat " + contrat.getIdContrat()
                    + " est deja affecte a la reservation " + contrat.getReservation().getIdReservation());
        }
        // Contrat est le cote proprietaire (cle etrangere reservation_id)
        contrat.setReservation(reservation);
        reservation.setContrat(contrat);
        return mapper.toDto(contratRepository.save(contrat));
    }

    private void valider(Contrat contrat) {
        BigDecimal montant = contrat.getMontantTotal();
        if (montant != null && montant.signum() < 0) {
            throw new IllegalArgumentException("Le montant total d'un contrat ne peut pas être négatif");
        }
    }
}
