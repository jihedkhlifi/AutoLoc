package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;

public record ClientResponseDto(
    Long idClient,
    String nom,
    String prenom,
    String email,
    String telephone,
    String numPermis,
    LocalDate dateInscription
) {}
