package com.moonboxorg.solidaritirbe;

import org.springframework.boot.SpringApplication;

public class TestSolidaritirbeApplication {

    public static void main(String[] args) {
        SpringApplication.from(SolidaritirbeApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
