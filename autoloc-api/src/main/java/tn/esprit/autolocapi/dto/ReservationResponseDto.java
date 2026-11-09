package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;

public record ReservationResponseDto(
    Long idReservation,
    LocalDate dateDebut,
    LocalDate dateFin,
    StatutReservation statut,
    Long vehiculeId,
    Long clientId
) {}
