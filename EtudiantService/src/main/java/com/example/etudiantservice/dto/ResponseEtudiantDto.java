package com.example.etudiantservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEtudiantDto
{
    private Integer idEtudiant;

    private String nom;
    private String prenom;
    private String cne;

    //private Integer idFiliere;
    private FiliereDto filiere;
}
