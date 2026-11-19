package tn.esprit.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocapi.domain.Equipement;
import tn.esprit.autolocapi.dto.EquipementInputDto;
import tn.esprit.autolocapi.dto.EquipementResponseDto;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.mapper.EquipementMapper;
import tn.esprit.autolocapi.repository.IEquipementRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipementServiceImpl implements IEquipementService {

    private static final String RESSOURCE = "Equipement";

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper mapper;

    @Override
    public EquipementResponseDto create(EquipementInputDto dto) {
        Equipement equipement = mapper.toEntity(dto);
        valider(equipement);
        return mapper.toDto(equipementRepository.save(equipement));
    }

    @Override
    public EquipementResponseDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    private Equipement getEntity(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, id));
    }

    @Override
    public List<EquipementResponseDto> findAll() {
        return equipementRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public EquipementResponseDto update(Long id, EquipementInputDto dto) {
        Equipement equipement = mapper.toEntity(dto);
        valider(equipement);
        Equipement existant = getEntity(id);
        existant.setLibelle(equipement.getLibelle());
        return mapper.toDto(equipementRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESSOURCE, id);
        }
        equipementRepository.deleteById(id);
    }

    private void valider(Equipement equipement) {
        if (equipement.getLibelle() == null || equipement.getLibelle().isBlank()) {
            throw new IllegalArgumentException("Le libellé de l'équipement est obligatoire");
        }
    }
}
