package com.moonboxorg.solidaritirbe;

import com.moonboxorg.solidaritirbe.repositories.ProvinceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class ProvinceControllerTest {

    private static final String PROVINCES_API_PATH = "/api/v1/provinces";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Test
    void shouldReturnProvincesWhenNoParamsProvided() throws Exception {
        // Test when no parameters are provided, should return all provinces
        mockMvc.perform(get(PROVINCES_API_PATH)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(110)));
    }

    @Test
    void shouldReturnProvinceByCode() throws Exception {
        // Test with a specific province code
        mockMvc.perform(get(PROVINCES_API_PATH)
                        .param("code", "PD")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.code").value("PD"))
                .andExpect(jsonPath("$.data.name").value("Padova"));
    }

    @Test
    void shouldReturnNotFoundForInvalidCode() throws Exception {
        // Test with an invalid province code
        mockMvc.perform(get(PROVINCES_API_PATH)
                        .param("code", "INVALID")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnProvinceByName() throws Exception {
        // Test with complete province name
        mockMvc.perform(get(PROVINCES_API_PATH)
                        .param("name", "Padova")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].code").value("PD"));

        // Test with partial province name
        mockMvc.perform(get(PROVINCES_API_PATH)
                        .param("name", "tr")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(6)))
                .andExpect(jsonPath("$.data[0].code").value("BT"));

        // Test with invalid province name
        mockMvc.perform(get(PROVINCES_API_PATH)
                        .param("name", "xx")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(NO_CONTENT.value()));
    }

    @Test
    void shouldReturnProvincesByRegion() throws Exception {
        // Test fetching provinces by region
        mockMvc.perform(get(PROVINCES_API_PATH)
                        .param("region", "Veneto")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(7)))
                .andExpect(jsonPath("$.data[0].name").value("Belluno"));
    }

    @Test
    void shouldReturnNoContentForInvalidRegion() throws Exception {
        // Test with a region that doesn't exist
        mockMvc.perform(get(PROVINCES_API_PATH)
                        .param("region", "InvalidRegion")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(NO_CONTENT.value()));
    }
}
