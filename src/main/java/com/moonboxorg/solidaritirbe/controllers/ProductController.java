package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.AddProductRequestDTO;
import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.dto.ProductResponseDTO;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredProductsInputModel;
import com.moonboxorg.solidaritirbe.services.impl.ProductServiceImpl;
import com.moonboxorg.solidaritirbe.utils.ApiResponseBuilder;
import com.moonboxorg.solidaritirbe.utils.validators.Ean13;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/products", produces = APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getProducts(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Boolean activeOnly,
            @RequestParam(required = false) @Ean13 String ean13
    ) throws ResourceNotFoundException {
        if (id != null)
            return ApiResponseBuilder.success(productService.getProductById(id));
        if (ean13 != null)
            return ApiResponseBuilder.success(productService.getProductByEan13(ean13));
        if (name == null && categoryId == null && activeOnly == null)
            return ApiResponseBuilder.success(productService.getAllProducts());

        var input = new GetFilteredProductsInputModel();
        input.setName(name);
        input.setCategoryId(categoryId);
        input.setActive(activeOnly != null && activeOnly);

        return ApiResponseBuilder.success(productService.getFilteredProducts(input));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> addProduct(
            @RequestBody @Valid AddProductRequestDTO addProductRequestDTO
    ) throws BadRequestException {
        return ApiResponseBuilder.created(productService.addProduct(addProductRequestDTO));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> deleteProduct(
            @RequestParam Long id
    ) throws ResourceNotFoundException {
        return ApiResponseBuilder.deleted(productService.deleteProductById(id));
    }
}
