package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;

public record PaiementResponseDto(
    Long idPaiement,
    BigDecimal montant,
    LocalDate datePaiement,
    ModePaiement modePaiement,
    Long contratId
) {}
