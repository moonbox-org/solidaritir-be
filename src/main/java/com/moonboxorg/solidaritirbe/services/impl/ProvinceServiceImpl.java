package com.moonboxorg.solidaritirbe.services.impl;

import com.moonboxorg.solidaritirbe.dto.ProvinceResponseDTO;
import com.moonboxorg.solidaritirbe.entities.CollectionPointEntity;
import com.moonboxorg.solidaritirbe.entities.ProvinceEntity;
import com.moonboxorg.solidaritirbe.repositories.ProvinceRepository;
import com.moonboxorg.solidaritirbe.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return getAll();
    }

    @Override
    public List<ProvinceResponseDTO> getAll() {
        return provinceRepository.findAll().stream()
                .map(this::mapToProvinceResponseDTO)
                .toList();
    }

    @Override
    public Optional<ProvinceResponseDTO> getProvinceEntityByCode(String code) {
        return provinceRepository.findByCode(code)
                .map(this::mapToProvinceResponseDTO);
    }

    @Override
    public List<ProvinceResponseDTO> getProvinceEntityByNameContainingIgnoreCase(String name) {
        return provinceRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToProvinceResponseDTO)
                .toList();
    }

    @Override
    public List<ProvinceResponseDTO> getProvinceEntityByRegion(String region) {
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
