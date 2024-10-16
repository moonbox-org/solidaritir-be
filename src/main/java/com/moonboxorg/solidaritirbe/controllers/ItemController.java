package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.AddItemRequestDTO;
import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.dto.ItemResponseDTO;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.models.GetFilteredItemsInputModel;
import com.moonboxorg.solidaritirbe.services.impl.ItemServiceImpl;
import com.moonboxorg.solidaritirbe.utils.ApiResponseBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<ApiResponse<ItemResponseDTO>> addItem(
            @RequestBody @Valid AddItemRequestDTO addItemRequestDTO
    ) throws ResourceNotFoundException {
        return ApiResponseBuilder.created(itemService.addItem(addItemRequestDTO));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Long>> deleteItem(
            @RequestParam Long id
    ) throws ResourceNotFoundException {
        return ApiResponseBuilder.deleted(itemService.deleteItemById(id));
    }
}
