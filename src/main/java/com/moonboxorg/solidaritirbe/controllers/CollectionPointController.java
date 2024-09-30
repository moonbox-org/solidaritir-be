package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.dto.CollectionPointResponseDTO;
import com.moonboxorg.solidaritirbe.services.impl.CollectionPointServiceImpl;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/cp", produces = "application/json")
public class CollectionPointController {

    private final CollectionPointServiceImpl collectionPointService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getCollectionPoints(
            @RequestParam(required = false) @Min(value = 1, message = "collection point code must be greater than 0") Long code,
            @RequestParam(required = false) String provCode,
            @RequestParam(required = false) String provName,
            @RequestParam(required = false) String regName
    ) {
        if (code != null) {
            Optional<CollectionPointResponseDTO> cp = collectionPointService.getCollectionPointByCode(code);
            if (cp.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse<>(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), null), NOT_FOUND);
            }
            return ResponseEntity.ok(new ApiResponse<>(OK.value(), OK.getReasonPhrase(), cp.get()));
        }

        List<CollectionPointResponseDTO> result = collectionPointService.getFilteredCollectionPoints(provCode, provName, regName);

        if (result.isEmpty())
            return new ResponseEntity<>(new ApiResponse<>(NO_CONTENT.value(), NO_CONTENT.getReasonPhrase(), null), OK);

        return ResponseEntity.ok(new ApiResponse<>(OK.value(), OK.getReasonPhrase(), result));
    }
}
