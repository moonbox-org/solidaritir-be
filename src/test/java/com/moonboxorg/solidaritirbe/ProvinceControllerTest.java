package com.moonboxorg.solidaritirbe;

import com.moonboxorg.solidaritirbe.repositories.ProvinceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class ProvinceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Container
    static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:alpine")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")
            .withInitScript("init-test.sql");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresDB::getJdbcUrl);
        registry.add("spring.datasource.username", postgresDB::getUsername);
        registry.add("spring.datasource.password", postgresDB::getPassword);
    }

    @Test
    void shouldReturnProvincesWhenNoParamsProvided() throws Exception {
        // Test when no parameters are provided, should return all provinces
        mockMvc.perform(get("/api/v1/provinces")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(110)));
    }

    @Test
    void shouldReturnProvinceByCode() throws Exception {
        // Test with a specific province code
        mockMvc.perform(get("/api/v1/provinces")
                        .param("code", "PD")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("PD"))
                .andExpect(jsonPath("$.name").value("Padova"));
    }

    @Test
    void shouldReturnNotFoundForInvalidCode() throws Exception {
        // Test with an invalid province code
        mockMvc.perform(get("/api/v1/provinces")
                        .param("code", "INVALID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnProvinceByName() throws Exception {
        // Test with complete province name
        mockMvc.perform(get("/api/v1/provinces")
                        .param("name", "Padova")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].code").value("PD"));

        // Test with partial province name
        mockMvc.perform(get("/api/v1/provinces")
                        .param("name", "tr")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[0].code").value("BT"));

        // Test with invalid province name
        mockMvc.perform(get("/api/v1/provinces")
                        .param("name", "xx")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnProvincesByRegion() throws Exception {
        // Test fetching provinces by region
        mockMvc.perform(get("/api/v1/provinces")
                        .param("region", "Veneto")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].name").value("Belluno"));
    }

    @Test
    void shouldReturnNoContentForInvalidRegion() throws Exception {
        // Test with a region that doesn't exist
        mockMvc.perform(get("/api/v1/provinces")
                        .param("region", "InvalidRegion")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
