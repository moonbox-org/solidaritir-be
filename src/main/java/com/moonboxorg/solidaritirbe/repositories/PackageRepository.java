package com.moonboxorg.solidaritirbe.repositories;

import com.moonboxorg.solidaritirbe.entities.PackageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<PackageEntity, Long> {
}
