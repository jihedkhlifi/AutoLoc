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
public class ReservationMapper {
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;

    public ReservationResponseDto toDto(Reservation e) {
        return new ReservationResponseDto(
            e.getIdReservation(),
            e.getDateDebut(),
            e.getDateFin(),
            e.getStatut(),
            e.getVehicule() == null ? null : e.getVehicule().getIdVehicule(),
            e.getClient() == null ? null : e.getClient().getIdClient()
        );
    }

    public Reservation toEntity(ReservationInputDto dto) {
        Reservation e = new Reservation();
        e.setDateDebut(dto.dateDebut());
        e.setDateFin(dto.dateFin());
        e.setStatut(dto.statut());
        if (dto.vehiculeId() != null) e.setVehicule(vehiculeRepository.findById(dto.vehiculeId()).orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.vehiculeId())));
        if (dto.clientId() != null) e.setClient(clientRepository.findById(dto.clientId()).orElseThrow(() -> new ResourceNotFoundException("Client", dto.clientId())));
        return e;
    }
}
