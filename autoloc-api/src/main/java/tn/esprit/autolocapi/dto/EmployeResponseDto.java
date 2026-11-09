package tn.esprit.autolocapi.dto;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;
import tn.esprit.autolocapi.domain.*;

public record EmployeResponseDto(
    Long idEmploye,
    String nom,
    String prenom,
    RoleEmploye role,
    Long agenceId
) {}
