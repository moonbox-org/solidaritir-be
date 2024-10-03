package com.moonboxorg.solidaritirbe.services.impl;

import com.moonboxorg.solidaritirbe.dto.AddCategoryRequestDTO;
import com.moonboxorg.solidaritirbe.dto.CategoryResponseDTO;
import com.moonboxorg.solidaritirbe.entities.CategoryEntity;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl {

    private static final String CATEGORY_NOT_FOUND_MSG = "No category matching the requested search criteria";
    private static final String INVALID_PARENT_CATEGORY_MSG = "Parent category ID must be greater than 0";
    private static final String PARENT_CATEGORY_NOT_FOUND_MSG = "Parent category not found";
    private static final String PARENT_OR_SUB_CATEGORY_NOT_FOUND_MSG = "Parent or sub-category not found";
    private static final String CATEGORY_WITH_SAME_NAME_ALREADY_EXISTS_MSG = "A category with the same name already exists";

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDTO> getCategoriesTree() {
        List<CategoryEntity> rootCategories = categoryRepository.findByParentCategoryIsNull();
        return rootCategories.stream()
                .map(this::convertToCategoryResponseDTO)
                .toList();
    }

    public CategoryResponseDTO getCategoryById(Long id) throws ResourceNotFoundException {
        return categoryRepository.findById(id)
                .map(this::convertToCategoryResponseDTOFirstLevelOnly)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MSG));
    }

    public CategoryResponseDTO getCategoryByName(String name) throws ResourceNotFoundException {
        return categoryRepository.findByName(name)
                .map(this::convertToCategoryResponseDTOFirstLevelOnly)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MSG));
    }

    @Transactional
    public CategoryResponseDTO addCategory(AddCategoryRequestDTO dto) throws BadRequestException, ResourceNotFoundException {
        // DTO validation
        validateAddCategoryRequestDTO(dto);

        if (dto.getParentId() == null) {
            // add new root category
            CategoryEntity categoryToSave = new CategoryEntity();
            categoryToSave.setName(dto.getName());
            categoryToSave.setParentCategory(null);
            categoryToSave.setDescription(dto.getDescription());
            return convertToCategoryResponseDTO(categoryRepository.save(categoryToSave));
        }
        // add new sub-category
        CategoryEntity categoryToSave = new CategoryEntity();
        categoryToSave.setName(dto.getName());
        categoryToSave.setDescription(dto.getDescription());
        return convertToCategoryResponseDTOFirstLevelOnly(addSubCategory(dto.getParentId(), categoryToSave));
    }

    @Transactional
    public Long deleteCategory(Long id) throws ResourceNotFoundException {
        CategoryEntity categoryToDelete = categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORY_NOT_FOUND_MSG));
        if (categoryToDelete.getParentCategory() != null) {
            log.info("Deleting sub-category with ID: {} from parent category with ID: {}",
                    categoryToDelete.getId(),
                    categoryToDelete.getParentCategory().getId()
            );
            CategoryEntity updatedParent = removeSubCategory(categoryToDelete.getParentCategory().getId(), categoryToDelete.getId());
            return updatedParent.getId();
        } else {
            log.info("Deleting root category with ID: {}", categoryToDelete.getId());
            categoryRepository.deleteById(id);
            return id;
        }
    }

    // ----- Helper methods ----- //

    /**
     * Adds a new sub-category to an existing category.
     *
     * @param parentId ID of the parent category.
     * @param subCat   The sub-category to add.
     * @return The updated parent category.
     */
    @Transactional
    private CategoryEntity addSubCategory(Long parentId, CategoryEntity subCat) throws ResourceNotFoundException {
        CategoryEntity parent = categoryRepository
                .findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException(PARENT_CATEGORY_NOT_FOUND_MSG));
        CategoryEntity newSubCategory = new CategoryEntity();
        newSubCategory.setName(subCat.getName());
        newSubCategory.setDescription(subCat.getDescription());
        newSubCategory.setParentCategory(parent);
        parent.addSubCategory(newSubCategory);
        return categoryRepository.save(parent);
    }

    /**
     * Removes a sub-category from a parent category.
     *
     * @param parentId ID of the parent category.
     * @param subCatId ID of the sub-category to remove.
     * @return The updated parent category.
     */
    @Transactional
    private CategoryEntity removeSubCategory(Long parentId, Long subCatId) throws ResourceNotFoundException {
        Optional<CategoryEntity> parentOpt = categoryRepository.findById(parentId);
        Optional<CategoryEntity> subCatOpt = categoryRepository.findById(subCatId);
        if (parentOpt.isEmpty() || subCatOpt.isEmpty())
            throw new ResourceNotFoundException(PARENT_OR_SUB_CATEGORY_NOT_FOUND_MSG);
        CategoryEntity parent = parentOpt.get();
        CategoryEntity subCategory = subCatOpt.get();
        parent.removeSubCategory(subCategory);
        return categoryRepository.save(parent);
    }

    private CategoryResponseDTO convertToCategoryResponseDTO(CategoryEntity category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null);
        dto.setSubCategories(category.getSubCategories().stream()
                .map(this::convertToCategoryResponseDTO)
                .collect(Collectors.toSet()));
        return dto;
    }

    private CategoryResponseDTO convertToCategoryResponseDTOFirstLevelOnly(CategoryEntity category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null);
        // Map only the first level of subcategories
        dto.setSubCategories(category.getSubCategories().stream()
                .map(this::convertToCategoryResponseDTOWithoutSubcategories) // Prevent deeper levels
                .collect(Collectors.toSet()));
        return dto;
    }

    private CategoryResponseDTO convertToCategoryResponseDTOWithoutSubcategories(CategoryEntity category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setParentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null);
        // No further subcategories
        dto.setSubCategories(Collections.emptySet());

        return dto;
    }

    private void validateAddCategoryRequestDTO(AddCategoryRequestDTO dto) throws BadRequestException {
        if (dto.getParentId() != null && dto.getParentId() <= 0) {
            throw new BadRequestException(INVALID_PARENT_CATEGORY_MSG);
        }
        if (categoryRepository.existsByName(dto.getName())) {
            throw new BadRequestException(CATEGORY_WITH_SAME_NAME_ALREADY_EXISTS_MSG);
        }
    }
}
