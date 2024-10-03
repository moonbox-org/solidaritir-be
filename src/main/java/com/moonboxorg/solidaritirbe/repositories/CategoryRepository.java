package com.moonboxorg.solidaritirbe.repositories;

import com.moonboxorg.solidaritirbe.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    // Find all root categories (categories without a parent)
    List<CategoryEntity> findByParentCategoryIsNull();

    // Find all sub-categories of a specific category
    List<CategoryEntity> findByParentCategoryId(Long parentId);

    Optional<CategoryEntity> findByName(String name);

    boolean existsByName(String name);
}
