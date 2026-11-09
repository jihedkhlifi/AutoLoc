package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record VehiculeInputDto(
    @NotBlank String immatriculation,
    @NotBlank String marque,
    @NotBlank String modele,
    @NotNull CategorieVehicule categorie,
    @NotNull @DecimalMin("0.0") BigDecimal tarifJournalier,
    @NotNull StatutVehicule statut,
    @Positive Long agenceId,
    List<Long> equipementIds
) {}
