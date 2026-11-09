package tn.esprit.autolocapi.mapper;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import tn.esprit.autolocapi.domain.*;
import tn.esprit.autolocapi.dto.*;
import tn.esprit.autolocapi.repository.*;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import java.util.List;
import java.util.ArrayList;
@Component
@RequiredArgsConstructor
public class ClientMapper {


    public ClientResponseDto toDto(Client e) {
        return new ClientResponseDto(
            e.getIdClient(),
            e.getNom(),
            e.getPrenom(),
            e.getEmail(),
            e.getTelephone(),
            e.getNumPermis(),
            e.getDateInscription()
        );
    }

    public Client toEntity(ClientInputDto dto) {
        Client e = new Client();
        e.setNom(dto.nom());
        e.setPrenom(dto.prenom());
        e.setEmail(dto.email());
        e.setTelephone(dto.telephone());
        e.setNumPermis(dto.numPermis());
        e.setDateInscription(dto.dateInscription());
        return e;
    }
}
