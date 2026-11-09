package tn.esprit.autolocapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autolocapi.dto.VehiculeInputDto;
import tn.esprit.autolocapi.dto.VehiculeResponseDto;
import tn.esprit.autolocapi.mapper.VehiculeMapper;
import jakarta.validation.Valid;
import tn.esprit.autolocapi.service.IVehiculeService;

import java.util.List;

@Tag(name = "Vehicules", description = "Gestion des vehicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;
    private final VehiculeMapper mapper;

    @Operation(summary = "Creer un vehicule")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creation reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeResponseDto create(@Valid @RequestBody VehiculeInputDto dto) {
        return mapper.toDto(vehiculeService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Lister les vehicules")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Liste retournee") })
    @GetMapping
    public List<VehiculeResponseDto> findAll() {
        return vehiculeService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter un vehicule")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @GetMapping("/{id}")
    public VehiculeResponseDto findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        return mapper.toDto(vehiculeService.findById(id));
    }

    @Operation(summary = "Modifier un vehicule")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Mise a jour reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PutMapping("/{id}")
    public VehiculeResponseDto update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @Valid @RequestBody VehiculeInputDto dto) {
        return mapper.toDto(vehiculeService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un vehicule")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
