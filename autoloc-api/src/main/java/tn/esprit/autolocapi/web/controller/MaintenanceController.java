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
import tn.esprit.autolocapi.dto.MaintenanceInputDto;
import tn.esprit.autolocapi.dto.MaintenanceResponseDto;
import tn.esprit.autolocapi.mapper.MaintenanceMapper;
import jakarta.validation.Valid;
import tn.esprit.autolocapi.service.IMaintenanceService;

import java.util.List;

@Tag(name = "Maintenances", description = "Gestion de la maintenance des vehicules")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;
    private final MaintenanceMapper mapper;

    @Operation(summary = "Creer une maintenance")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creation reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MaintenanceResponseDto create(@Valid @RequestBody MaintenanceInputDto dto) {
        return mapper.toDto(maintenanceService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Lister les maintenances")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Liste retournee") })
    @GetMapping
    public List<MaintenanceResponseDto> findAll() {
        return maintenanceService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter une maintenance")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @GetMapping("/{id}")
    public MaintenanceResponseDto findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        return mapper.toDto(maintenanceService.findById(id));
    }

    @Operation(summary = "Modifier une maintenance")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Mise a jour reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PutMapping("/{id}")
    public MaintenanceResponseDto update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @Valid @RequestBody MaintenanceInputDto dto) {
        return mapper.toDto(maintenanceService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer une maintenance")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
