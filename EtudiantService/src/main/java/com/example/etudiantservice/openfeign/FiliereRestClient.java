package com.example.etudiantservice.openfeign;

import com.example.etudiantservice.dto.FiliereDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "FilieresService", url = "${filiere.service.url}", configuration = FeignClientConfig.class)
public interface FiliereRestClient
{
    @GetMapping("/{id}")
    FiliereDto getFiliereById(@PathVariable("id") Integer id);
}
