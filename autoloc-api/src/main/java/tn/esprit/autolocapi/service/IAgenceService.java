package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.AgenceInputDto;
import tn.esprit.autolocapi.dto.AgenceResponseDto;

import java.util.List;

public interface IAgenceService {

    AgenceResponseDto create(AgenceInputDto dto);

    AgenceResponseDto findById(Long id);

    List<AgenceResponseDto> findAll();

    AgenceResponseDto update(Long id, AgenceInputDto dto);

    void deleteById(Long id);
}
