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
import tn.esprit.autolocapi.dto.EquipementInputDto;
import tn.esprit.autolocapi.dto.EquipementResponseDto;
import tn.esprit.autolocapi.mapper.EquipementMapper;
import jakarta.validation.Valid;
import tn.esprit.autolocapi.service.IEquipementService;

import java.util.List;

@Tag(name = "Equipements", description = "Gestion des equipements")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;
    private final EquipementMapper mapper;

    @Operation(summary = "Creer un equipement")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creation reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementResponseDto create(@Valid @RequestBody EquipementInputDto dto) {
        return mapper.toDto(equipementService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Lister les equipements")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Liste retournee") })
    @GetMapping
    public List<EquipementResponseDto> findAll() {
        return equipementService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter un equipement")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @GetMapping("/{id}")
    public EquipementResponseDto findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        return mapper.toDto(equipementService.findById(id));
    }

    @Operation(summary = "Modifier un equipement")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Mise a jour reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PutMapping("/{id}")
    public EquipementResponseDto update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @Valid @RequestBody EquipementInputDto dto) {
        return mapper.toDto(equipementService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un equipement")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
