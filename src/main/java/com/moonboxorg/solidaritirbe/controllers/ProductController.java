package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredProductsInputModel;
import com.moonboxorg.solidaritirbe.services.impl.ProductServiceImpl;
import com.moonboxorg.solidaritirbe.utils.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(required = false) boolean activeOnly,
            @RequestParam(required = false) String ean13
    ) throws ResourceNotFoundException, BadRequestException {
        if (id != null)
            return ApiResponseBuilder.success(productService.getProductById(id));
        if (ean13 != null)
            return ApiResponseBuilder.success(productService.getProductByEan13(ean13));

        var input = new GetFilteredProductsInputModel();
        input.setName(name);
        input.setCategoryId(categoryId);
        input.setActive(activeOnly);

        return ApiResponseBuilder.success(productService.getFilteredProducts(input));
    }
}
