package com.moonboxorg.solidaritirbe.repositories;

import com.moonboxorg.solidaritirbe.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByActive(boolean active);

    List<ProductEntity> findByNameContainingIgnoreCase(String name);

    List<ProductEntity> findByNameContainingIgnoreCaseAndActive(String name, boolean active);

    List<ProductEntity> findByCategory_Id(Long categoryId);

    List<ProductEntity> findByCategory_IdAndActive(Long categoryId, boolean active);

    Optional<ProductEntity> findByEan13(String ean13);
}
