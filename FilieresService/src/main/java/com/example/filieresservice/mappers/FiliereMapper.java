package com.example.filieresservice.mappers;

import com.example.filieresservice.dto.RequestFiliereDto;
import com.example.filieresservice.dto.ResponseFiliereDto;
import com.example.filieresservice.entities.Filiere;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class FiliereMapper
{
    public Filiere DTO_to_Entity(RequestFiliereDto requestFiliereDto)
    {
        Filiere filiere = new Filiere();
        BeanUtils.copyProperties(requestFiliereDto, filiere);
        return filiere;
    }

    public ResponseFiliereDto Entity_to_DTO(Filiere filiere)
    {
        ResponseFiliereDto responseFiliereDto = new ResponseFiliereDto();
        BeanUtils.copyProperties(filiere, responseFiliereDto);
        return responseFiliereDto;
    }
}
