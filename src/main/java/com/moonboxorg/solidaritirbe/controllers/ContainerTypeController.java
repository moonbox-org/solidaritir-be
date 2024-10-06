package com.moonboxorg.solidaritirbe.controllers;

import com.moonboxorg.solidaritirbe.services.impl.ContainerTypeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/container-types", produces = APPLICATION_JSON_VALUE)
public class ContainerTypeController {

    private final ContainerTypeServiceImpl containerTypeService;
}
