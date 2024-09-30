package com.moonboxorg.solidaritirbe.repositories;

import com.moonboxorg.solidaritirbe.entities.CollectionPointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CollectionPointRepository extends JpaRepository<CollectionPointEntity, Long> {

    Optional<CollectionPointEntity> getCollectionPointByCode(Long code);

    List<CollectionPointEntity> getCollectionPointsByProvinceCode(String provinceCode);

    List<CollectionPointEntity> getCollectionPointsByProvinceName(String provinceName);

    List<CollectionPointEntity> getCollectionPointsByProvinceRegion(String regionName);
}
