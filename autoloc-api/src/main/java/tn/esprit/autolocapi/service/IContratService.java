package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.ContratInputDto;
import tn.esprit.autolocapi.dto.ContratResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface IContratService {

    ContratResponseDto create(ContratInputDto dto);

    ContratResponseDto findById(Long id);

    List<ContratResponseDto> findAll();

    ContratResponseDto update(Long id, ContratInputDto dto);

    void deleteById(Long id);

    // ---- Service d'affectation

    ContratResponseDto affecterContratAReservation(LocalDate dateSignature, LocalDate dateDebut, LocalDate dateFin);
}
