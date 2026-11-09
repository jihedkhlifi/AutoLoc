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
import tn.esprit.autolocapi.dto.ContratInputDto;
import tn.esprit.autolocapi.dto.ContratResponseDto;
import tn.esprit.autolocapi.mapper.ContratMapper;
import jakarta.validation.Valid;
import tn.esprit.autolocapi.service.IContratService;

import java.util.List;

@Tag(name = "Contrats", description = "Gestion des contrats de location")
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;
    private final ContratMapper mapper;

    @Operation(summary = "Creer un contrat")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creation reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContratResponseDto create(@Valid @RequestBody ContratInputDto dto) {
        return mapper.toDto(contratService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Lister les contrats")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Liste retournee") })
    @GetMapping
    public List<ContratResponseDto> findAll() {
        return contratService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter un contrat")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @GetMapping("/{id}")
    public ContratResponseDto findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        return mapper.toDto(contratService.findById(id));
    }

    @Operation(summary = "Modifier un contrat")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Mise a jour reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PutMapping("/{id}")
    public ContratResponseDto update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @Valid @RequestBody ContratInputDto dto) {
        return mapper.toDto(contratService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un contrat")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        contratService.deleteById(id);
    }
}
