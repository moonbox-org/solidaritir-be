package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.dto.ApiResponse;
import com.moonboxorg.solidaritirbe.dto.ProvinceResponseDTO;
import com.moonboxorg.solidaritirbe.services.impl.ProvinceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/provinces", produces = "application/json")
public class ProvinceController {

    private final ProvinceServiceImpl provinceService;

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getProvinces(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String region
    ) {
        if (Strings.isNotBlank(code)) {
            Optional<ProvinceResponseDTO> province = provinceService.getProvinceEntityByCode(code);
            if (province.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse<>(NOT_FOUND.value(), NOT_FOUND.getReasonPhrase(), null), NOT_FOUND);
            }
            return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), province.get()));
        }

        List<ProvinceResponseDTO> provinces = provinceService.getFilteredProvinces(name, region);

        if (provinces.isEmpty())
            return new ResponseEntity<>(new ApiResponse<>(NO_CONTENT.value(), NO_CONTENT.getReasonPhrase(), null), OK);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), provinces));
    }
}
