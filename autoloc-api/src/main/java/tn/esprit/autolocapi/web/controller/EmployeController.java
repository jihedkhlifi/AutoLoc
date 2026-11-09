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
import tn.esprit.autolocapi.dto.EmployeInputDto;
import tn.esprit.autolocapi.dto.EmployeResponseDto;
import tn.esprit.autolocapi.mapper.EmployeMapper;
import jakarta.validation.Valid;
import tn.esprit.autolocapi.service.IEmployeService;

import java.util.List;

@Tag(name = "Employes", description = "Gestion des employes")
@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;
    private final EmployeMapper mapper;

    @Operation(summary = "Creer un employe")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creation reussie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeResponseDto create(@Valid @RequestBody EmployeInputDto dto) {
        return mapper.toDto(employeService.create(mapper.toEntity(dto)));
    }

    @Operation(summary = "Lister les employes")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Liste retournee") })
    @GetMapping
    public List<EmployeResponseDto> findAll() {
        return employeService.findAll().stream().map(mapper::toDto).toList();
    }

    @Operation(summary = "Consulter un employe")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Ressource trouvee"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @GetMapping("/{id}")
    public EmployeResponseDto findById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        return mapper.toDto(employeService.findById(id));
    }

    @Operation(summary = "Modifier un employe")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Mise a jour reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides") })
    @PutMapping("/{id}")
    public EmployeResponseDto update(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id, @Valid @RequestBody EmployeInputDto dto) {
        return mapper.toDto(employeService.update(id, mapper.toEntity(dto)));
    }

    @Operation(summary = "Supprimer un employe")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Suppression reussie"),
            @ApiResponse(responseCode = "404", description = "Ressource introuvable") })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "Identifiant de la ressource") @PathVariable Long id) {
        employeService.deleteById(id);
    }
}
