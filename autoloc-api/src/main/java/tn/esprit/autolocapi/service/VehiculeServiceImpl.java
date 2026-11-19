package tn.esprit.autolocapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolocapi.domain.Equipement;
import tn.esprit.autolocapi.domain.Maintenance;
import tn.esprit.autolocapi.domain.StatutVehicule;
import tn.esprit.autolocapi.domain.Vehicule;
import tn.esprit.autolocapi.dto.VehiculeInputDto;
import tn.esprit.autolocapi.dto.VehiculeResponseDto;
import tn.esprit.autolocapi.exception.ConflitException;
import tn.esprit.autolocapi.exception.ResourceNotFoundException;
import tn.esprit.autolocapi.mapper.VehiculeMapper;
import tn.esprit.autolocapi.repository.IEquipementRepository;
import tn.esprit.autolocapi.repository.IMaintenanceRepository;
import tn.esprit.autolocapi.repository.IVehiculeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private static final String RESSOURCE = "Vehicule";

    private final IVehiculeRepository vehiculeRepository;
    private final IEquipementRepository equipementRepository;
    private final IMaintenanceRepository maintenanceRepository;
    private final VehiculeMapper mapper;

    @Override
    public VehiculeResponseDto create(VehiculeInputDto dto) {
        Vehicule vehicule = mapper.toEntity(dto);
        valider(vehicule);
        return mapper.toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    public VehiculeResponseDto findById(Long id) {
        return mapper.toDto(getEntity(id));
    }

    private Vehicule getEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(RESSOURCE, id));
    }

    @Override
    public List<VehiculeResponseDto> findAll() {
        return vehiculeRepository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public VehiculeResponseDto update(Long id, VehiculeInputDto dto) {
        Vehicule vehicule = mapper.toEntity(dto);
        valider(vehicule);
        Vehicule existant = getEntity(id);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        if (vehicule.getAgence() != null) existant.setAgence(vehicule.getAgence());
        if (vehicule.getEquipements() != null) existant.setEquipements(vehicule.getEquipements());
        return mapper.toDto(vehiculeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException(RESSOURCE, id);
        }
        vehiculeRepository.deleteById(id);
    }

    // ------------------------------------------------------------------
    // Services d'affectation
    // ------------------------------------------------------------------

    /** Ajoute l'equipement idEquipement a la liste des equipements du vehicule idVehicule. */
    @Override
    @Transactional
    public VehiculeResponseDto affecterVehiculeAEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getEntity(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));

        boolean dejaAffecte = vehicule.getEquipements().stream()
                .anyMatch(e -> e.getIdEquipement().equals(idEquipement));
        if (dejaAffecte) {
            throw new ConflitException("L'equipement " + idEquipement
                    + " est deja affecte au vehicule " + idVehicule);
        }
        // Vehicule est le cote proprietaire du * ---- * : c'est lui qui alimente la table vehicule_equipement
        vehicule.getEquipements().add(equipement);
        return mapper.toDto(vehiculeRepository.save(vehicule));
    }

    /**
     * Affecte la maintenance identifiee par sa description au vehicule identifie par son immatriculation.
     * Si la maintenance est en cours aujourd'hui, le vehicule passe au statut MAINTENANCE.
     */
    @Override
    @Transactional
    public VehiculeResponseDto affecterMaintenanceAvehicule(String description, String immatriculation) {
        Maintenance maintenance = ResultatUnique.exiger(
                maintenanceRepository.findByDescriptionIgnoreCase(description.trim()),
                "maintenance avec la description '" + description + "'");
        Vehicule vehicule = ResultatUnique.exiger(
                vehiculeRepository.findByImmatriculationIgnoreCase(immatriculation.trim()),
                "vehicule immatricule " + immatriculation);

        Vehicule actuel = maintenance.getVehicule();
        if (actuel != null && !actuel.getIdVehicule().equals(vehicule.getIdVehicule())) {
            throw new ConflitException("Cette maintenance est deja affectee au vehicule "
                    + actuel.getImmatriculation());
        }
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);

        if (estEnCours(maintenance)) {
            vehicule.setStatut(StatutVehicule.MAINTENANCE);
            vehicule = vehiculeRepository.save(vehicule);
        }
        return mapper.toDto(vehicule);
    }

    private static boolean estEnCours(Maintenance maintenance) {
        LocalDate aujourdhui = LocalDate.now();
        boolean commencee = maintenance.getDateDebut() == null || !maintenance.getDateDebut().isAfter(aujourdhui);
        boolean pasTerminee = maintenance.getDateFin() == null || !maintenance.getDateFin().isBefore(aujourdhui);
        return commencee && pasTerminee;
    }

    private void valider(Vehicule vehicule) {
        BigDecimal tarif = vehicule.getTarifJournalier();
        if (tarif != null && tarif.signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }
}
