package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;

public record VehiculeResponseDto(
    Long idVehicule,
    String immatriculation,
    String marque,
    String modele,
    CategorieVehicule categorie,
    BigDecimal tarifJournalier,
    StatutVehicule statut,
    Long agenceId,
    List<Long> equipementIds
) {}
