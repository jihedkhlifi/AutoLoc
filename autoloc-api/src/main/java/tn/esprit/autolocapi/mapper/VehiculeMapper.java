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
public class VehiculeMapper {
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;

    public VehiculeResponseDto toDto(Vehicule e) {
        return new VehiculeResponseDto(
            e.getIdVehicule(),
            e.getImmatriculation(),
            e.getMarque(),
            e.getModele(),
            e.getCategorie(),
            e.getTarifJournalier(),
            e.getStatut(),
            e.getAgence() == null ? null : e.getAgence().getIdAgence(),
            e.getEquipements() == null ? List.of() : e.getEquipements().stream().map(x -> x.getIdEquipement()).toList()
        );
    }

    public Vehicule toEntity(VehiculeInputDto dto) {
        Vehicule e = new Vehicule();
        e.setImmatriculation(dto.immatriculation());
        e.setMarque(dto.marque());
        e.setModele(dto.modele());
        e.setCategorie(dto.categorie());
        e.setTarifJournalier(dto.tarifJournalier());
        e.setStatut(dto.statut());
        if (dto.agenceId() != null) e.setAgence(agenceRepository.findById(dto.agenceId()).orElseThrow(() -> new ResourceNotFoundException("Agence", dto.agenceId())));
        if (dto.equipementIds() != null) {
            e.setEquipements(dto.equipementIds().stream().map(id -> equipementRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Equipement", id))).toList());
        }
        return e;
    }
}
