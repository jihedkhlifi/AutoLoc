package tn.esprit.autolocapi.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import tn.esprit.autolocapi.domain.ModePaiement;

public record PaiementInputDto(
    @NotNull @DecimalMin("0.0") BigDecimal montant,
    @NotNull LocalDate datePaiement,
    @NotNull ModePaiement modePaiement
) {}
