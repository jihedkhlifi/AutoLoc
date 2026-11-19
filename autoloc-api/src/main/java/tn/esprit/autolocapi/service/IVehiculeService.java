package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.VehiculeInputDto;
import tn.esprit.autolocapi.dto.VehiculeResponseDto;

import java.util.List;

public interface IVehiculeService {

    VehiculeResponseDto create(VehiculeInputDto dto);

    VehiculeResponseDto findById(Long id);

    List<VehiculeResponseDto> findAll();

    VehiculeResponseDto update(Long id, VehiculeInputDto dto);

    void deleteById(Long id);

    // ---- Services d'affectation

    VehiculeResponseDto affecterVehiculeAEquipement(Long idVehicule, Long idEquipement);

    VehiculeResponseDto affecterMaintenanceAvehicule(String description, String immatriculation);
}
