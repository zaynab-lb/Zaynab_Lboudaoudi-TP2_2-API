package com.example.filieresservice.service;

import com.example.filieresservice.dto.RequestFiliereDto;
import com.example.filieresservice.dto.ResponseFiliereDto;
import com.example.filieresservice.entities.Filiere;
import com.example.filieresservice.mappers.FiliereMapper;
import com.example.filieresservice.repository.FiliereRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FiliereServiceImp implements FiliereService
{
    private FiliereRepository filiereRepository;
    private FiliereMapper filiereMapper;

    public FiliereServiceImp(FiliereRepository filiereRepository, FiliereMapper filiereMapper)
    {
        this.filiereRepository = filiereRepository;
        this.filiereMapper = filiereMapper;
    }

    @Override
    public ResponseFiliereDto AddFiliere(RequestFiliereDto requestFiliereDto)
    {
        Filiere filiere = filiereMapper.DTO_to_Entity(requestFiliereDto);
        Filiere savedFiliere = filiereRepository.save(filiere);

        return filiereMapper.Entity_to_DTO(savedFiliere);
    }

    @Override
    public List<ResponseFiliereDto> GetAllFilieres()
    {
        List<Filiere> filieres = filiereRepository.findAll();
        List<ResponseFiliereDto> filiereDtos = new ArrayList<>();
        for (Filiere f : filieres)
        {
            filiereDtos.add(filiereMapper.Entity_to_DTO(f));
        }

        return filiereDtos;
    }

    @Override
    public ResponseFiliereDto GetFiliereById(Integer id)
    {
        Filiere filiere = filiereRepository.findById(id).orElseThrow();
        return filiereMapper.Entity_to_DTO(filiere);
    }

    @Override
    public ResponseFiliereDto UpdateFiliere(Integer id, RequestFiliereDto requestFiliereDto)
    {
        Filiere newFiliere = filiereMapper.DTO_to_Entity(requestFiliereDto);

        Filiere filiere = filiereRepository.findById(id).orElseThrow();

        if(newFiliere.getCode()!=null) filiere.setCode(newFiliere.getCode());
        if(newFiliere.getLibelle()!=null) filiere.setLibelle(newFiliere.getLibelle());

        Filiere saved_filiere = filiereRepository.save(filiere);
        return filiereMapper.Entity_to_DTO(saved_filiere);
    }

    @Override
    public void DeleteFiliere(Integer id)
    {
        filiereRepository.deleteById(id);
    }
}
