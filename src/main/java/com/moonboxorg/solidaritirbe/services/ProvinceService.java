package com.moonboxorg.solidaritirbe.services;

import com.moonboxorg.solidaritirbe.dto.ProvinceResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {

    List<ProvinceResponseDTO> getAll();

    Optional<ProvinceResponseDTO> getProvinceEntityByCode(String code);

    List<ProvinceResponseDTO> getProvinceEntityByNameContainingIgnoreCase(String name);

    List<ProvinceResponseDTO> getProvinceEntityByRegion(String region);
}
