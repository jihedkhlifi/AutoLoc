package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.PaiementResponseDto;

import java.util.List;

public interface IPaiementService {

    PaiementResponseDto findById(Long id);

    List<PaiementResponseDto> findAll();
}
