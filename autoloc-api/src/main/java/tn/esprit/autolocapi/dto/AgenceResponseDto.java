package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;

public record AgenceResponseDto(
    Long idAgence,
    String nom,
    String ville,
    String adresse,
    String telephone
) {}
