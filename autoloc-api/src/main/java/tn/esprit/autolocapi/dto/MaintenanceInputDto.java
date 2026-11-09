package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record MaintenanceInputDto(
    @NotNull LocalDate dateDebut,
    LocalDate dateFin,
    @NotBlank String description,
    @Positive Long vehiculeId
) {
    @AssertTrue(message = "La date de fin doit etre posterieure ou egale a la date de debut")
    public boolean isPeriodeValide() {
        return dateDebut == null || dateFin == null || !dateFin.isBefore(dateDebut);
    }
}
