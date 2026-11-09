package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;

public record ContratResponseDto(
    Long idContrat,
    LocalDate dateSignature,
    BigDecimal montantTotal,
    boolean valide,
    Long reservationId,
    List<PaiementResponseDto> paiements
) {}
