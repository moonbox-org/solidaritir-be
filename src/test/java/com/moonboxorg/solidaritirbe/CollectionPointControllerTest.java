package com.moonboxorg.solidaritirbe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonboxorg.solidaritirbe.dto.CreateCollectionPointRequestDTO;
import com.moonboxorg.solidaritirbe.repositories.CollectionPointRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class CollectionPointControllerTest {

    private static final String CP_API_PATH = "/api/v1/cp";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CollectionPointRepository collectionPointRepository;

    @Test
    void shouldReturnCollectionPointsWhenNoParamsProvided() throws Exception {
        // Test when no parameters are provided, should return all collection points
        mockMvc.perform(get(CP_API_PATH)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)));
    }

    @Test
    void shouldReturnCollectionPointByCode() throws Exception {
        // Test with a specific collection point code
        mockMvc.perform(get(CP_API_PATH)
                        .param("code", "1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.code").value("1"))
                .andExpect(jsonPath("$.data.name").value("PD-01"));
    }

    @Test
    void shouldReturnBadRequestForInvalidCode() throws Exception {
        // Test with an invalid collection point code
        mockMvc.perform(get(CP_API_PATH)
                        .param("code", "-1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCollectionPointByProvCode() throws Exception {
        // Test with a specific province code
        mockMvc.perform(get(CP_API_PATH)
                        .param("provCode", "PD")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].code").value("1"))
                .andExpect(jsonPath("$.data[0].name").value("PD-01"));
    }

    @Test
    void shouldReturnNoContentForInvalidProvCode() throws Exception {
        // Test with a specific province code
        mockMvc.perform(get(CP_API_PATH)
                        .param("provCode", "invalid")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(NO_CONTENT.value()));
    }

    @Test
    void shouldReturnCollectionPointByProvName() throws Exception {
        // Test with a specific province code
        mockMvc.perform(get(CP_API_PATH)
                        .param("provName", "Padova")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].code").value("1"))
                .andExpect(jsonPath("$.data[0].name").value("PD-01"));
    }

    @Test
    void shouldReturnCollectionPointByRegName() throws Exception {
        // Test with a specific province code
        mockMvc.perform(get(CP_API_PATH)
                        .param("regName", "Veneto")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)));
    }

    @Test
    void shouldCreateCollectionPoint() throws Exception {
        CreateCollectionPointRequestDTO dto = new CreateCollectionPointRequestDTO();
        dto.setName("PD-03");
        dto.setProvinceCode("PD");
        dto.setActive(true);
        dto.setNotes("test notes");

        mockMvc.perform(post(CP_API_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("PD-03"));
    }

    @Test
    void shouldReturnBadRequestForInvalidRequest() throws Exception {
        CreateCollectionPointRequestDTO dto = new CreateCollectionPointRequestDTO();
        dto.setName("PD-03");
        dto.setProvinceCode("invalid");
        dto.setActive(true);
        dto.setNotes("test notes");

        mockMvc.perform(post(CP_API_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}
