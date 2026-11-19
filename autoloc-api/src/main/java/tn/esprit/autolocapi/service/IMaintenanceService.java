package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.MaintenanceInputDto;
import tn.esprit.autolocapi.dto.MaintenanceResponseDto;

import java.util.List;

public interface IMaintenanceService {

    MaintenanceResponseDto create(MaintenanceInputDto dto);

    MaintenanceResponseDto findById(Long id);

    List<MaintenanceResponseDto> findAll();

    MaintenanceResponseDto update(Long id, MaintenanceInputDto dto);

    void deleteById(Long id);
}
