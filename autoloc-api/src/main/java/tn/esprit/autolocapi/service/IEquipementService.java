package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.EquipementInputDto;
import tn.esprit.autolocapi.dto.EquipementResponseDto;

import java.util.List;

public interface IEquipementService {

    EquipementResponseDto create(EquipementInputDto dto);

    EquipementResponseDto findById(Long id);

    List<EquipementResponseDto> findAll();

    EquipementResponseDto update(Long id, EquipementInputDto dto);

    void deleteById(Long id);
}
