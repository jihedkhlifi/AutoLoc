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
public class EquipementMapper {


    public EquipementResponseDto toDto(Equipement e) {
        return new EquipementResponseDto(
            e.getIdEquipement(),
            e.getLibelle()
        );
    }

    public Equipement toEntity(EquipementInputDto dto) {
        Equipement e = new Equipement();
        e.setLibelle(dto.libelle());
        return e;
    }
}
