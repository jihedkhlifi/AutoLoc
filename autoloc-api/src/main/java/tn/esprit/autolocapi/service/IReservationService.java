package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.ReservationInputDto;
import tn.esprit.autolocapi.dto.ReservationResponseDto;
import tn.esprit.autolocapi.dto.ReservationWithContratRequestDTO;

import java.util.List;

public interface IReservationService {

    ReservationResponseDto create(ReservationInputDto dto);

    ReservationResponseDto findById(Long id);

    List<ReservationResponseDto> findAll();

    ReservationResponseDto update(Long id, ReservationInputDto dto);

    void deleteById(Long id);

    // ---- Services d'affectation

    ReservationResponseDto affecterReservationAClient(Long idReservation, String nom, String prenom);

    ReservationResponseDto creerReservationEtContratAssocie(ReservationWithContratRequestDTO requestDTO);
}
