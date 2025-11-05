package com.example.etudiantservice.service;

import com.example.etudiantservice.dto.RequestEtudiantDto;
import com.example.etudiantservice.dto.ResponseEtudiantDto;

import java.util.List;

public interface EtudiantService
{
    public ResponseEtudiantDto AddEtudiant(RequestEtudiantDto requestEtudiantDto);
    public List<ResponseEtudiantDto> GetAllEtudiant();
    public ResponseEtudiantDto GetEtudiantById(Integer id);
    public ResponseEtudiantDto UpdateEtudiant(Integer id, RequestEtudiantDto requestEtudiantDto);
    public void DeleteEtudiant(Integer id);
}
