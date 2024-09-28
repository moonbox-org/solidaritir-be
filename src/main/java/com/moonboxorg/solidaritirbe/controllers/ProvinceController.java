package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.services.impl.ProvinceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/provinces", produces = "application/json")
public class ProvinceController {

    private final ProvinceServiceImpl provinceService;

    @GetMapping
    public ResponseEntity<?> getProvinces(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String region
    ) {
        if (Strings.isNotBlank(code)) {
            return provinceService.getProvinceEntityByCode(code)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        if (Strings.isNotBlank(name)) {
            return provinceService.getProvinceEntityByNameContainingIgnoreCase(name)
                    .isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(provinceService.getProvinceEntityByNameContainingIgnoreCase(name));
        }
        if (Strings.isNotBlank(region)) {
            return provinceService.getProvinceEntityByRegion(region)
                    .isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(provinceService.getProvinceEntityByRegion(region));
        }
        return ResponseEntity.ok(provinceService.getAll());
    }
}
