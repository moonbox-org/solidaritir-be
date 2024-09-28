package com.moonboxorg.solidaritirbe.services;

import com.moonboxorg.solidaritirbe.entities.ProvinceEntity;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {

    List<ProvinceEntity> getAll();

    Optional<ProvinceEntity> getProvinceEntityByCode(String code);

    List<ProvinceEntity> getProvinceEntityByNameContainingIgnoreCase(String name);

    List<ProvinceEntity> getProvinceEntityByRegion(String region);
}
