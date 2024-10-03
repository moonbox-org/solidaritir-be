package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.dto.CollectionPointResponseDTO;
import com.moonboxorg.solidaritirbe.dto.CreateCollectionPointRequestDTO;
import com.moonboxorg.solidaritirbe.services.impl.CollectionPointServiceImpl;
import com.moonboxorg.solidaritirbe.utils.ApiResponseBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/cp", produces = APPLICATION_JSON_VALUE)
public class CollectionPointController {

    private static final String CP_CODE_VALIDATION_MSG = "Collection point code must be greater than 0";
    private static final String CP_NOT_FOUND_MSG = "Collection point not found";

    private final CollectionPointServiceImpl collectionPointService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getCollectionPoints(
            @RequestParam(required = false) @Min(value = 1, message = CP_CODE_VALIDATION_MSG) Long code,
            @RequestParam(required = false) String provCode,
            @RequestParam(required = false) String provName,
            @RequestParam(required = false) String regName
    ) {
        if (code != null) {
            Optional<CollectionPointResponseDTO> cp = collectionPointService.getCollectionPointByCode(code);
            return cp.<ResponseEntity<ApiResponse<Object>>>map(ApiResponseBuilder::success)
                    .orElseGet(() -> ApiResponseBuilder.notFound(CP_NOT_FOUND_MSG));
        }

        List<CollectionPointResponseDTO> result = collectionPointService.getFilteredCollectionPoints(provCode, provName, regName);

        if (result.isEmpty())
            return ApiResponseBuilder.noContent();
        return ApiResponseBuilder.success(result);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createCollectionPoint(
            @RequestBody @Valid CreateCollectionPointRequestDTO createCpDTO
    ) {
        return ApiResponseBuilder.created(collectionPointService.createCollectionPoint(createCpDTO));
    }
}
