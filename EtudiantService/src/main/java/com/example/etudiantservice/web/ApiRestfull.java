package com.example.etudiantservice.web;

import com.example.etudiantservice.dto.RequestEtudiantDto;
import com.example.etudiantservice.dto.ResponseEtudiantDto;
import com.example.etudiantservice.service.EtudiantServiceImp;
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
                title = "Gestion des étudiants",
                description = "Cette API offre toutes les méthodes pour gérer les étudiant en relation avec les filiére",
                version = "1.0"
        ),
        servers = @Server(
                url = "http://localhost:8082"
        )
)
@RestController
@RequestMapping("/v1/etudiants")
public class ApiRestfull
{
    private EtudiantServiceImp  etudiantService;

    public ApiRestfull(EtudiantServiceImp etudiantService)
    {
        this.etudiantService = etudiantService;
    }

    @Operation(
            summary = "Ajouter nouveau étudiant s'inscrire dans une filiére",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseEtudiantDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Etudiant bien ajouter",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEtudiantDto.class)
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
    public ResponseEntity<ResponseEtudiantDto> add(@RequestBody RequestEtudiantDto requestEtudiantDto)
    {
        ResponseEtudiantDto responseEtudiantDto = etudiantService.AddEtudiant(requestEtudiantDto);
        return ResponseEntity.ok(responseEtudiantDto);
    }

    @Operation(
            summary = "Afficher la liste des étudiants",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La liste est bien afficher",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ResponseEtudiantDto.class))
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
    public ResponseEntity<List<ResponseEtudiantDto>> getAll()
    {
        List<ResponseEtudiantDto> etudiantDtos = etudiantService.GetAllEtudiant();
        return ResponseEntity.ok(etudiantDtos);
    }

    @Operation(
            summary = "Afficher les informations d'un étudiant par son id",
            parameters = @Parameter(
                    name = "id",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Etudiant bien affiché",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEtudiantDto.class)
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
    public ResponseEntity<ResponseEtudiantDto> get(@PathVariable Integer id)
    {
        ResponseEtudiantDto etudiantDto = etudiantService.GetEtudiantById(id);
        return ResponseEntity.ok(etudiantDto);

    }

    @Operation(
            summary = "Modofier les informations d'un étudiant",
            parameters = @Parameter(
                    name = "id",
                    required = true
            ),
            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RequestEtudiantDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "L'étudiant' est bien modifier",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ResponseEtudiantDto.class)
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
    public ResponseEntity<ResponseEtudiantDto> update(@PathVariable Integer id, @RequestBody RequestEtudiantDto requestEtudiantDto)
    {
        ResponseEtudiantDto responseEtudiantDto = etudiantService.UpdateEtudiant(id, requestEtudiantDto);
        return ResponseEntity.ok(responseEtudiantDto);
    }

    @Operation(
            summary = "La suppression d'un étudiant par son id",
            parameters = @Parameter(
                    name = "id",
                    required = true
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Etudiant bien supprimer"
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
        etudiantService.DeleteEtudiant(id);
        return ResponseEntity.ok().build();
    }
}
