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
public class AgenceMapper {


    public AgenceResponseDto toDto(Agence e) {
        return new AgenceResponseDto(
            e.getIdAgence(),
            e.getNom(),
            e.getVille(),
            e.getAdresse(),
            e.getTelephone()
        );
    }

    public Agence toEntity(AgenceInputDto dto) {
        Agence e = new Agence();
        e.setNom(dto.nom());
        e.setVille(dto.ville());
        e.setAdresse(dto.adresse());
        e.setTelephone(dto.telephone());
        return e;
    }
}
