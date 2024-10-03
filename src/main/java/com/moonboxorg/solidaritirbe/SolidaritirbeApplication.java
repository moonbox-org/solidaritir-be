package com.moonboxorg.solidaritirbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SolidaritirbeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolidaritirbeApplication.class, args);
    }

}
