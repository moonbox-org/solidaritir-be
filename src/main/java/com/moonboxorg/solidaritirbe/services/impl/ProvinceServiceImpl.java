package com.moonboxorg.solidaritirbe.services.impl;

import com.moonboxorg.solidaritirbe.entities.ProvinceEntity;
import com.moonboxorg.solidaritirbe.repositories.ProvinceRepository;
import com.moonboxorg.solidaritirbe.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Override
    public List<ProvinceEntity> getAll() {
        return provinceRepository.findAll();
    }

    @Override
    public Optional<ProvinceEntity> getProvinceEntityByCode(String code) {
        return provinceRepository.findByCode(code);
    }

    @Override
    public List<ProvinceEntity> getProvinceEntityByNameContainingIgnoreCase(String name) {
        return provinceRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<ProvinceEntity> getProvinceEntityByRegion(String region) {
        return provinceRepository.findByRegionIgnoreCase(region);
    }
}
