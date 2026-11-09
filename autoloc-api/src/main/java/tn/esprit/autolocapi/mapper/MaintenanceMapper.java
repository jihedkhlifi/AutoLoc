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
public class MaintenanceMapper {
    private final IVehiculeRepository vehiculeRepository;

    public MaintenanceResponseDto toDto(Maintenance e) {
        return new MaintenanceResponseDto(
            e.getIdMaintenance(),
            e.getDateDebut(),
            e.getDateFin(),
            e.getDescription(),
            e.getVehicule() == null ? null : e.getVehicule().getIdVehicule()
        );
    }

    public Maintenance toEntity(MaintenanceInputDto dto) {
        Maintenance e = new Maintenance();
        e.setDateDebut(dto.dateDebut());
        e.setDateFin(dto.dateFin());
        e.setDescription(dto.description());
        if (dto.vehiculeId() != null) e.setVehicule(vehiculeRepository.findById(dto.vehiculeId()).orElseThrow(() -> new ResourceNotFoundException("Vehicule", dto.vehiculeId())));
        return e;
    }
}
