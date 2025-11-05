package com.example.etudiantservice.service;

import com.example.etudiantservice.dto.FiliereDto;
import com.example.etudiantservice.dto.RequestEtudiantDto;
import com.example.etudiantservice.dto.ResponseEtudiantDto;
import com.example.etudiantservice.entities.Etudiant;
import com.example.etudiantservice.mappers.EtudiantMapper;
import com.example.etudiantservice.openfeign.FiliereRestClient;
import com.example.etudiantservice.repository.EtudiantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EtudiantServiceImp implements EtudiantService
{
    private EtudiantRepository etudiantRepository;
    private EtudiantMapper  etudiantMapper;
    private FiliereRestClient filiereRestClient;

    public EtudiantServiceImp(EtudiantRepository etudiantRepository, EtudiantMapper etudiantMapper, FiliereRestClient filiereRestClient)
    {
        this.etudiantRepository = etudiantRepository;
        this.etudiantMapper = etudiantMapper;
        this.filiereRestClient = filiereRestClient;
    }

    @Override
    public ResponseEtudiantDto AddEtudiant(RequestEtudiantDto requestEtudiantDto)
    {
        Etudiant etudiant = etudiantMapper.DTO_to_Entity(requestEtudiantDto);

        FiliereDto filiere;
        try {
            filiere = filiereRestClient.getFiliereById(etudiant.getIdFiliere());
            if (filiere == null) {
                throw new RuntimeException("Filière introuvable avec ID: " + etudiant.getIdFiliere());
            }
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'ajouter l'étudiant : la filière n'existe pas (ID = " + etudiant.getIdFiliere() + ")");
        }

        Etudiant savedEtudiant = etudiantRepository.save(etudiant);
        ResponseEtudiantDto responseEtudiantDto = etudiantMapper.Entity_to_DTO(savedEtudiant);
        responseEtudiantDto.setFiliere(filiere);

        return responseEtudiantDto;
    }

    @Override
    public List<ResponseEtudiantDto> GetAllEtudiant()
    {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        List<ResponseEtudiantDto> etudiantDtos = new ArrayList<>();
        for (Etudiant e : etudiants)
        {
            ResponseEtudiantDto responseEtudiantDto = etudiantMapper.Entity_to_DTO(e);

            try {
                FiliereDto filiere = filiereRestClient.getFiliereById(e.getIdFiliere());
                responseEtudiantDto.setFiliere(filiere);
            } catch (Exception ex) {
                responseEtudiantDto.setFiliere(null);
            }

            etudiantDtos.add(responseEtudiantDto);
        }
        return etudiantDtos;
    }

    @Override
    public ResponseEtudiantDto GetEtudiantById(Integer id)
    {
        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow();

        ResponseEtudiantDto responseEtudiantDto = etudiantMapper.Entity_to_DTO(etudiant);

        try {
            FiliereDto filiere = filiereRestClient.getFiliereById(etudiant.getIdFiliere());
            responseEtudiantDto.setFiliere(filiere);
        } catch (Exception e) {
            responseEtudiantDto.setFiliere(null);
        }

        return responseEtudiantDto;
    }

    @Override
    public ResponseEtudiantDto UpdateEtudiant(Integer id, RequestEtudiantDto requestEtudiantDto)
    {
        Etudiant newEtudiant = etudiantMapper.DTO_to_Entity(requestEtudiantDto);

        Etudiant etudiant = etudiantRepository.findById(id).orElseThrow();

        if(newEtudiant.getNom()!=null) etudiant.setNom(newEtudiant.getNom());
        if(newEtudiant.getPrenom()!=null) etudiant.setPrenom(newEtudiant.getPrenom());
        if(newEtudiant.getCne()!=null) etudiant.setCne(newEtudiant.getCne());
        if(newEtudiant.getIdFiliere()!=null) etudiant.setIdFiliere(newEtudiant.getIdFiliere());

        Etudiant   saved_etudiant = etudiantRepository.save(etudiant);
        ResponseEtudiantDto responseEtudiantDto = etudiantMapper.Entity_to_DTO(saved_etudiant);

        try {
            FiliereDto filiere = filiereRestClient.getFiliereById(saved_etudiant.getIdFiliere());
            responseEtudiantDto.setFiliere(filiere);
        } catch (Exception e) {
            responseEtudiantDto.setFiliere(null);
        }

        return responseEtudiantDto;
    }

    @Override
    public void DeleteEtudiant(Integer id)
    {
        etudiantRepository.deleteById(id);
    }
}
