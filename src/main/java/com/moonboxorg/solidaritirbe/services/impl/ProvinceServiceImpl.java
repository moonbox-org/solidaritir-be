package com.moonboxorg.solidaritirbe.services.impl;

import com.moonboxorg.solidaritirbe.dto.ProvinceResponseDTO;
import com.moonboxorg.solidaritirbe.entities.CollectionPointEntity;
import com.moonboxorg.solidaritirbe.entities.ProvinceEntity;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.repositories.ProvinceRepository;
import com.moonboxorg.solidaritirbe.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    public List<ProvinceResponseDTO> getFilteredProvinces(String name, String region) {
        if (Strings.isNotBlank(name)) {
            return getProvinceEntityByNameContainingIgnoreCase(name);
        }
        if (Strings.isNotBlank(region)) {
            return getProvinceEntityByRegion(region);
        }
        log.info("No filter applied, fetching all provinces");
        return getAll();
    }

    @Override
    public List<ProvinceResponseDTO> getAll() {
        return provinceRepository.findAll().stream()
                .map(this::mapToProvinceResponseDTO)
                .toList();
    }

    @Override
    public ProvinceResponseDTO getProvinceEntityByCode(String code) throws ResourceNotFoundException {
        log.info("Fetching province by code: {}", code);
        return provinceRepository.findByCode(code)
                .map(this::mapToProvinceResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Province not found for code: " + code));
    }

    @Override
    public List<ProvinceResponseDTO> getProvinceEntityByNameContainingIgnoreCase(String name) {
        log.info("Fetching provinces by name containing: {}", name);
        return provinceRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToProvinceResponseDTO)
                .toList();
    }

    @Override
    public List<ProvinceResponseDTO> getProvinceEntityByRegion(String region) {
        log.info("Fetching provinces by region: {}", region);
        return provinceRepository.findByRegionIgnoreCase(region).stream()
                .map(this::mapToProvinceResponseDTO)
                .toList();
    }

    private ProvinceResponseDTO mapToProvinceResponseDTO(ProvinceEntity p) {
        return new ProvinceResponseDTO(
                p.getCode(),
                p.getName(),
                p.getRegion(),
                p.getCollectionPoints().stream().map(CollectionPointEntity::getCode).toList(),
                p.getCreatedAt(),
                p.getCreatedBy(),
                p.getLastUpdatedAt(),
                p.getLastUpdatedBy()
        );
    }
}
