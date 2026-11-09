package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record ClientInputDto(
    @NotBlank String nom,
    @NotBlank String prenom,
    @NotBlank @Email String email,
    String telephone,
    String numPermis,
    LocalDate dateInscription
) {}
