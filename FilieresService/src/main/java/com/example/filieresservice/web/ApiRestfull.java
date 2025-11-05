package com.example.filieresservice.web;

import com.example.filieresservice.dto.RequestFiliereDto;
import com.example.filieresservice.dto.ResponseFiliereDto;
import com.example.filieresservice.service.FiliereServiceImp;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Gestion des filiéres",
                description = "Cette API offre toutes les méthodes pour gérer les filiére",
                version = "1.0"
        ),
        servers = @Server(
                url = "http://localhost:8081"
        )
)
@RestController
@RequestMapping("/v1/filieres")
public class ApiRestfull
{
    private FiliereServiceImp filiereService;

    public ApiRestfull(FiliereServiceImp filiereService)
    {
        this.filiereService = filiereService;
    }

    @Operation(
            summary = "Ajouter nouvelle filiére",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RequestFiliereDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fiéliere bien ajouté",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseFiliereDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "4xx",
                            description = "Erreur client"
                    ),
                    @ApiResponse(
                            responseCode = "5xx",
                            description = "Erreur serveur"
                    )
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseFiliereDto> add(@RequestBody RequestFiliereDto requestFiliereDto)
    {
        ResponseFiliereDto responseFiliereDto = filiereService.AddFiliere(requestFiliereDto);
        return ResponseEntity.ok(responseFiliereDto);
    }

    @Operation(
            summary = "Afficher la liste des filiére",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La liste est bien afficher",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = RequestFiliereDto.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "4xx",
                            description = "Erreur client"
                    ),
                    @ApiResponse(
                            responseCode = "5xx",
                            description = "Erreur serveur"
                    )
            }
    )
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<ResponseFiliereDto>> getAll()
    {
        List<ResponseFiliereDto> filiereDtos = filiereService.GetAllFilieres();
        return ResponseEntity.ok(filiereDtos);
    }

    @Operation(
            summary = "Afficher les informations d'une filiére par son id",
            parameters = @Parameter(
                    name = "id",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Filiére bien affiché",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseFiliereDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "4xx",
                            description = "Erreur Client"
                    ),
                    @ApiResponse(
                            responseCode = "5xx",
                            description = "Erreur Serveur"
                    )
            }
    )
    @PreAuthorize("hasAnyAuthority('SCOPE_USER', 'SCOPE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseFiliereDto> get(@PathVariable Integer id)
    {
        ResponseFiliereDto filiereDto = filiereService.GetFiliereById(id);
        return ResponseEntity.ok(filiereDto);

    }

    @Operation(
            summary = "Modofier les informations d'une filiére",
            parameters = @Parameter(
                    name = "id",
                    required = true
            ),
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RequestFiliereDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La filiére est bien modifier",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseFiliereDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "4xx",
                            description = "Erreur client"
                    ),
                    @ApiResponse(
                            responseCode = "5xx",
                            description = "Erreur serveur"
                    )
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseFiliereDto> update(@PathVariable Integer id, @RequestBody RequestFiliereDto requestFiliereDto)
    {
        ResponseFiliereDto responseFiliereDto = filiereService.UpdateFiliere(id, requestFiliereDto);
        return ResponseEntity.ok(responseFiliereDto);
    }

    @Operation(
            summary = "La suppression d'une filiére par son id",
            parameters = @Parameter(
                    name = "id",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Filiére bien supprimer"
                    ),
                    @ApiResponse(
                            responseCode = "4xx",
                            description = "Erreur client"
                    ),
                    @ApiResponse(
                            responseCode = "5xx",
                            description = "Erreur serveur"
                    )
            }
    )
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id)
    {
        filiereService.DeleteFiliere(id);
        return ResponseEntity.ok().build();
    }
}
