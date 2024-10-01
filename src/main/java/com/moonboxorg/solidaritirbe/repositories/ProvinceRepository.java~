package com.moonboxorg.solidaritirbe.repositories;

import com.moonboxorg.solidaritirbe.entities.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<ProvinceEntity, String> {
    Optional<ProvinceEntity> findByCode(String code);

    List<ProvinceEntity> findByNameContainingIgnoreCase(String name);

    List<ProvinceEntity> findByRegionIgnoreCase(String region);
}
