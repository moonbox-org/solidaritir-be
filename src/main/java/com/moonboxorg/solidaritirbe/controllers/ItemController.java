package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredItemsInputModel;
import com.moonboxorg.solidaritirbe.services.impl.ItemServiceImpl;
import com.moonboxorg.solidaritirbe.utils.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(value = "/api/v1/items", produces = APPLICATION_JSON_VALUE)
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getItems(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long packageId,
            @RequestParam(required = false) Integer expInDays
    ) throws ResourceNotFoundException {
        if (id != null)
            return ApiResponseBuilder.success(itemService.getItemById(id));

        var input = new GetFilteredItemsInputModel();
        input.setProductId(productId);
        input.setCategoryId(categoryId);
        input.setPackageId(packageId);
        input.setExpInDays(expInDays);

        return ApiResponseBuilder.success(itemService.getFilteredItems(input));
    }
}
