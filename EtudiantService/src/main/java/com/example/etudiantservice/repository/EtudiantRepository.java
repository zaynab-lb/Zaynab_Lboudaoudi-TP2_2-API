package com.example.etudiantservice.repository;

import com.example.etudiantservice.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EtudiantRepository extends JpaRepository<Etudiant, Integer>
{
}
