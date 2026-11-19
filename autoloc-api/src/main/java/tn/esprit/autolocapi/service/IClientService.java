package tn.esprit.autolocapi.service;

import tn.esprit.autolocapi.dto.ClientInputDto;
import tn.esprit.autolocapi.dto.ClientResponseDto;

import java.util.List;

public interface IClientService {

    ClientResponseDto create(ClientInputDto dto);

    ClientResponseDto findById(Long id);

    List<ClientResponseDto> findAll();

    ClientResponseDto update(Long id, ClientInputDto dto);

    void deleteById(Long id);
}
