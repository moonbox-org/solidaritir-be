package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.AddCategoryRequestDTO;
import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.dto.CategoryResponseDTO;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.services.impl.CategoryServiceImpl;
import com.moonboxorg.solidaritirbe.utils.ApiResponseBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/categories", produces = APPLICATION_JSON_VALUE)
public class CategoryController {

    private static final String CATEGORY_ID_VALIDATION_MSG = "Category ID must be greater than 0";

    private final CategoryServiceImpl categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getCategoriesTree(
            @RequestParam(required = false) @Min(value = 1, message = CATEGORY_ID_VALIDATION_MSG) Long id,
            @RequestParam(required = false) String name
    ) throws ResourceNotFoundException {
        if (id != null)
            return ApiResponseBuilder.success(categoryService.getCategoryById(id));
        if (Strings.isNotBlank(name))
            return ApiResponseBuilder.success(categoryService.getCategoryByName(name.trim()));
        return ApiResponseBuilder.success(categoryService.getCategoriesTree());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> addCategory(
            @RequestBody @Valid AddCategoryRequestDTO dto
    ) throws BadRequestException, ResourceNotFoundException {
        return ApiResponseBuilder.created(categoryService.addCategory(dto));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> deleteCategory(
            @RequestParam @Min(value = 1, message = CATEGORY_ID_VALIDATION_MSG) Long id
    ) throws ResourceNotFoundException {
        return ApiResponseBuilder.deleted(categoryService.deleteCategory(id));
    }
}
