package tn.esprit.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autolocapi.dto.PaiementInputDto;
import tn.esprit.autolocapi.dto.PaiementResponseDto;
import tn.esprit.autolocapi.mapper.PaiementMapper;
import jakarta.validation.Valid;
import tn.esprit.autolocapi.service.IPaiementService;

import java.util.List;

@Tag(name = "Paiements", description = "Consultation des paiements (lecture seule)")
@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final IPaiementService paiementService;
    private final PaiementMapper mapper;

    @Operation(summary = "Lister les paiements")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Liste retournee") })
    @GetMapping
    public List<PaiementResponseDto> findAll() {
        return paiementService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter un paiement")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @GetMapping("/{id}")
    public PaiementResponseDto findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        return mapper.toDto(paiementService.findById(id));
    }
}
