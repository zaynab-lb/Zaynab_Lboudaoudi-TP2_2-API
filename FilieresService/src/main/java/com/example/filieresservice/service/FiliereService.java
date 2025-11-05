package com.example.filieresservice.service;

import com.example.filieresservice.dto.RequestFiliereDto;
import com.example.filieresservice.dto.ResponseFiliereDto;

import java.util.List;

public interface FiliereService
{
    public ResponseFiliereDto AddFiliere(RequestFiliereDto requestFiliereDto);
    public List<ResponseFiliereDto> GetAllFilieres();
    public ResponseFiliereDto GetFiliereById(Integer id);
    public ResponseFiliereDto UpdateFiliere(Integer id , RequestFiliereDto requestFiliereDto);
    public void DeleteFiliere(Integer id);
}
