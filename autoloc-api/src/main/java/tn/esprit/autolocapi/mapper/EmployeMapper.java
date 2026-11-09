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
public class EmployeMapper {
    private final IAgenceRepository agenceRepository;

    public EmployeResponseDto toDto(Employe e) {
        return new EmployeResponseDto(
            e.getIdEmploye(),
            e.getNom(),
            e.getPrenom(),
            e.getRole(),
            e.getAgence() == null ? null : e.getAgence().getIdAgence()
        );
    }

    public Employe toEntity(EmployeInputDto dto) {
        Employe e = new Employe();
        e.setNom(dto.nom());
        e.setPrenom(dto.prenom());
        e.setRole(dto.role());
        if (dto.agenceId() != null) e.setAgence(agenceRepository.findById(dto.agenceId()).orElseThrow(() -> new ResourceNotFoundException("Agence", dto.agenceId())));
        return e;
    }
}
