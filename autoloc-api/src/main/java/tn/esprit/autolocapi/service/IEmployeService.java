package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.EmployeInputDto;
import tn.esprit.autolocapi.dto.EmployeResponseDto;

import java.util.List;

public interface IEmployeService {

    EmployeResponseDto create(EmployeInputDto dto);

    EmployeResponseDto findById(Long id);

    List<EmployeResponseDto> findAll();

    EmployeResponseDto update(Long id, EmployeInputDto dto);

    void deleteById(Long id);
}
