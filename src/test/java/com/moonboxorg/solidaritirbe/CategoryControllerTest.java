package com.moonboxorg.solidaritirbe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moonboxorg.solidaritirbe.dto.AddCategoryRequestDTO;
import com.moonboxorg.solidaritirbe.dto.CategoryResponseDTO;
import com.moonboxorg.solidaritirbe.repositories.CategoryRepository;
import com.moonboxorg.solidaritirbe.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestcontainersConfiguration.class)
class CategoryControllerTest {

    private static final String CATEGORY_API_PATH = "/api/v1/categories";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    @Order(1)
    void shouldReturnCategoriesTree() throws Exception {
        mockMvc.perform(get(CATEGORY_API_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(5)));
    }

    @Test
    @Order(2)
    void shouldReturnCategoryById() throws Exception {
        mockMvc.perform(get(CATEGORY_API_PATH)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Perishables"));
    }

    @Test
    @Order(3)
    void shouldReturnCategoryByName() throws Exception {
        mockMvc.perform(get(CATEGORY_API_PATH)
                        .param("name", "Perishables"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Perishables"));
    }

    @Test
    @Order(4)
    void shouldReturnBadRequestForInvalidId() throws Exception {
        mockMvc.perform(get(CATEGORY_API_PATH)
                        .param("id", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(5)
    void shouldAddNewRootCategory() throws Exception {
        AddCategoryRequestDTO dto = new AddCategoryRequestDTO();
        dto.setName("root-cat-test");
        dto.setDescription("test");

        mockMvc.perform(post(CATEGORY_API_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(6)
    void shouldAddNewSubCategory() throws Exception {
        AddCategoryRequestDTO dto = new AddCategoryRequestDTO();
        dto.setParentId(1L);
        dto.setName("sub-cat-test");
        dto.setDescription("test");

        mockMvc.perform(post(CATEGORY_API_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(7)
    void shouldReturnBadRequestOnAddNewSubCategory() throws Exception {
        AddCategoryRequestDTO dto = new AddCategoryRequestDTO();
        dto.setParentId(1L);
        dto.setName("sub-cat-test");
        dto.setDescription("test");

        mockMvc.perform(post(CATEGORY_API_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    void shouldReturnBadRequestOnAddNewCategoryForInvalidParentId() throws Exception {
        AddCategoryRequestDTO dto = new AddCategoryRequestDTO();
        dto.setParentId(0L);
        dto.setName("test");
        dto.setDescription("test");

        mockMvc.perform(post(CATEGORY_API_PATH)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    void shouldDeleteCategory() throws Exception {
        mockMvc.perform(delete(CATEGORY_API_PATH)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(NO_CONTENT.value()))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    @Order(10)
    void shouldReturnBadRequestOnDeleteCategory() throws Exception {
        mockMvc.perform(delete(CATEGORY_API_PATH)
                        .param("id", "0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(11)
    void shouldDeleteSubCategory() throws Exception {
        AddCategoryRequestDTO rootCatDTO = new AddCategoryRequestDTO();
        rootCatDTO.setName("root-cat-test-2");
        rootCatDTO.setDescription("test");

        CategoryResponseDTO rootCatData = categoryService.addCategory(rootCatDTO);
        Long rootCatId = rootCatData.getId();

        AddCategoryRequestDTO subCatDTO1 = new AddCategoryRequestDTO();
        subCatDTO1.setParentId(rootCatId);
        subCatDTO1.setName("sub-cat-test-1");
        subCatDTO1.setDescription("test");

        AddCategoryRequestDTO subCatDTO2 = new AddCategoryRequestDTO();
        subCatDTO2.setParentId(rootCatId);
        subCatDTO2.setName("sub-cat-test-2");
        subCatDTO2.setDescription("test");

        categoryService.addCategory(subCatDTO1);
        CategoryResponseDTO subCat2Data = categoryService.addCategory(subCatDTO2);
        Long subCat2Id = subCat2Data.getSubCategories().iterator().next().getId();

        // get saved root category
        mockMvc.perform(get(CATEGORY_API_PATH)
                        .param("id", rootCatId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("root-cat-test-2"));

        // get saved sub category 1
        mockMvc.perform(get(CATEGORY_API_PATH)
                        .param("name", "sub-cat-test-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("sub-cat-test-1"));

        // get saved sub category 2
        mockMvc.perform(get(CATEGORY_API_PATH)
                        .param("name", "sub-cat-test-2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("sub-cat-test-2"));

        // delete sub category 2
        mockMvc.perform(delete(CATEGORY_API_PATH)
                        .param("id", subCat2Id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(NO_CONTENT.value()));

        // get root category and check subcategory 2 has been deleted
        mockMvc.perform(get(CATEGORY_API_PATH)
                        .param("id", rootCatId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.subCategories", hasSize(1)));

        // delete root category
        mockMvc.perform(delete(CATEGORY_API_PATH)
                        .param("id", rootCatId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(NO_CONTENT.value()));

        // check subcategory 1 and 2 have been deleted
        mockMvc.perform(get(CATEGORY_API_PATH)
                        .param("name", "sub-cat-test-2"))
                .andExpect(status().isNotFound());
    }
}
