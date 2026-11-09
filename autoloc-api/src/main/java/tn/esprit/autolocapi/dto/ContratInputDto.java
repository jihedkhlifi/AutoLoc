package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record ContratInputDto(
    LocalDate dateSignature,
    @DecimalMin("0.0") BigDecimal montantTotal,
    boolean valide,
    @Positive Long reservationId,
    @Valid List<@Valid PaiementInputDto> paiements
) {}
