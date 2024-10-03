package com.moonboxorg.solidaritirbe.services;

import com.moonboxorg.solidaritirbe.dto.ProvinceResponseDTO;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {

    List<ProvinceResponseDTO> getAll();

    ProvinceResponseDTO getProvinceEntityByCode(String code) throws ResourceNotFoundException;

    List<ProvinceResponseDTO> getProvinceEntityByNameContainingIgnoreCase(String name);

    List<ProvinceResponseDTO> getProvinceEntityByRegion(String region);
}
