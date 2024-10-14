package com.moonboxorg.solidaritirbe.repositories;

import com.moonboxorg.solidaritirbe.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    List<ItemEntity> findByProduct_Id(Long productId);

    List<ItemEntity> findByProduct_Category_Id(Long categoryId);

    List<ItemEntity> findByPackageEntity_Id(Long packageId);
}
