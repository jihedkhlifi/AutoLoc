package tn.esprit.autolocapi.mapper;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import tn.esprit.autolocapi.domain.*;
import tn.esprit.autolocapi.dto.*;
import tn.esprit.autolocapi.repository.*;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import java.util.List;
import java.util.ArrayList;
@Component
@RequiredArgsConstructor
public class ContratMapper {
    private final IReservationRepository reservationRepository;

    public ContratResponseDto toDto(Contrat e) {
        return new ContratResponseDto(
            e.getIdContrat(),
            e.getDateSignature(),
            e.getMontantTotal(),
            e.isValide(),
            e.getReservation() == null ? null : e.getReservation().getIdReservation(),
            e.getPaiements() == null ? List.of() : e.getPaiements().stream().map(p -> new PaiementResponseDto(p.getIdPaiement(), p.getMontant(), p.getDatePaiement(), p.getModePaiement(), e.getIdContrat())).toList()
        );
    }

    public Contrat toEntity(ContratInputDto dto) {
        Contrat e = new Contrat();
        e.setDateSignature(dto.dateSignature());
        e.setMontantTotal(dto.montantTotal());
        e.setValide(dto.valide());
        if (dto.reservationId() != null) e.setReservation(reservationRepository.findById(dto.reservationId()).orElseThrow(() -> new ResourceNotFoundException("Reservation", dto.reservationId())));
        if (dto.paiements() != null) {
            List<Paiement> paiements = new ArrayList<>();
            for (PaiementInputDto input : dto.paiements()) {
                Paiement p = new Paiement();
                p.setMontant(input.montant());
                p.setDatePaiement(input.datePaiement());
                p.setModePaiement(input.modePaiement());
                p.setContrat(e);
                paiements.add(p);
            }
            e.setPaiements(paiements);
        }
        return e;
    }
}
