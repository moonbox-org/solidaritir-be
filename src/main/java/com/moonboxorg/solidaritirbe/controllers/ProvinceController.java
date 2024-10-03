package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.dto.ProvinceResponseDTO;
import com.moonboxorg.solidaritirbe.exceptions.ResourceNotFoundException;
import com.moonboxorg.solidaritirbe.services.impl.ProvinceServiceImpl;
import com.moonboxorg.solidaritirbe.utils.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/provinces", produces = APPLICATION_JSON_VALUE)
public class ProvinceController {

    private final ProvinceServiceImpl provinceService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getProvinces(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String region
    ) throws ResourceNotFoundException {
        if (Strings.isNotBlank(code))
            return ApiResponseBuilder.success(provinceService.getProvinceEntityByCode(code));

        List<ProvinceResponseDTO> provinces = provinceService.getFilteredProvinces(name, region);
        if (provinces.isEmpty())
            return ApiResponseBuilder.noContent();
        return ApiResponseBuilder.success(provinces);
    }
}
