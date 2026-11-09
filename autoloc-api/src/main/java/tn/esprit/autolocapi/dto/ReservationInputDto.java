package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record ReservationInputDto(
    @NotNull LocalDate dateDebut,
    @NotNull LocalDate dateFin,
    @NotNull StatutReservation statut,
    @Positive Long vehiculeId,
    @Positive Long clientId
) {
    @AssertTrue(message = "La date de fin doit etre posterieure ou egale a la date de debut")
    public boolean isPeriodeValide() {
        return dateDebut == null || dateFin == null || !dateFin.isBefore(dateDebut);
    }
}
