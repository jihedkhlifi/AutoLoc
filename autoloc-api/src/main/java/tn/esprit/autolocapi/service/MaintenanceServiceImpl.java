package tn.esprit.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Maintenance;
import tn.esprit.autolocapi.dto.MaintenanceInputDto;
import tn.esprit.autolocapi.dto.MaintenanceResponseDto;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.mapper.MaintenanceMapper;
import tn.esprit.autolocapi.repository.IMaintenanceRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl implements IMaintenanceService {

    private static final String RESSOURCE = "Maintenance";

    private final IMaintenanceRepository maintenanceRepository;
    private final MaintenanceMapper mapper;

    @Override
    public MaintenanceResponseDto create(MaintenanceInputDto dto) {
        Maintenance maintenance = mapper.toEntity(dto);
        valider(maintenance);
        return mapper.toDto(maintenanceRepository.save(maintenance));
    }

    @Override
    public MaintenanceResponseDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    private Maintenance getEntity(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, id));
    }

    @Override
    public List<MaintenanceResponseDto> findAll() {
        return maintenanceRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public MaintenanceResponseDto update(Long id, MaintenanceInputDto dto) {
        Maintenance maintenance = mapper.toEntity(dto);
        valider(maintenance);
        Maintenance existante = getEntity(id);
        existante.setDateDebut(maintenance.getDateDebut());
        existante.setDateFin(maintenance.getDateFin());
        existante.setDescription(maintenance.getDescription());
        if (maintenance.getVehicule() != null) existante.setVehicule(maintenance.getVehicule());
        return mapper.toDto(maintenanceRepository.save(existante));
    }

    @Override
    public void deleteById(Long id) {
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESSOURCE, id);
        }
        maintenanceRepository.deleteById(id);
    }

    private void valider(Maintenance maintenance) {
        // pas d'attribut "coût" dans le modèle : seule la cohérence des dates est contrôlée
        if (maintenance.getDateDebut() != null && maintenance.getDateFin() != null
                && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }
}
