package com.example.etudiantservice.mappers;

import com.example.etudiantservice.dto.RequestEtudiantDto;
import com.example.etudiantservice.dto.ResponseEtudiantDto;
import com.example.etudiantservice.entities.Etudiant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EtudiantMapper
{
    public Etudiant DTO_to_Entity(RequestEtudiantDto requestEtudiantDto)
    {
        Etudiant etudiant = new Etudiant();
        BeanUtils.copyProperties(requestEtudiantDto, etudiant);
        return etudiant;
    }

    public ResponseEtudiantDto Entity_to_DTO(Etudiant etudiant)
    {
        ResponseEtudiantDto responseEtudiantDto = new ResponseEtudiantDto();
        BeanUtils.copyProperties(etudiant, responseEtudiantDto);
        return responseEtudiantDto;
    }
}
