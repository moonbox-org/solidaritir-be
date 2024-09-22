package com.moonboxorg.solidaritirbe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {

    @GetMapping("/demo")
    private void demo() {
        log.info("Hello World");
    }
}
