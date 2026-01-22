package com.ejemplo.apiproductos.controller;

import com.ejemplo.apiproductos.dto.ProductResponseDto;
import com.ejemplo.apiproductos.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private final String VALID_API_KEY = "producto-api-linktic";

    @Test
    void shouldCreateProductAndReturn201() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake-image-content".getBytes()
        );

        // 👉 MOCKEAMOS EL RETORNO DEL SERVICE
        ProductResponseDto response = new ProductResponseDto();
        response.setId(UUID.randomUUID());
        response.setName("Test Laptop");
        response.setDescription("Laptop para testing");
        response.setPrice(new BigDecimal("1200.00"));

        Mockito.when(productService.create(Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/products")
                        .file(imageFile)
                        .param("name", "Test Laptop")
                        .param("price", "1200.00")
                        .param("description", "Laptop para testing")
                        .param("state", "ACTIVE")
                        .header("X-API-KEY", VALID_API_KEY))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.type").value("products"))
                .andExpect(jsonPath("$.data.attributes.name").value("Test Laptop"))
                .andExpect(jsonPath("$.data.id").exists());
    }

    @Test
    void shouldReturn401WhenNoApiKey() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.errors[0].status").value("401"))
                .andExpect(jsonPath("$.errors[0].detail").value(containsString("Invalid or missing X-API-KEY")));
    }

    @Test
    void shouldReturn400WhenInvalidData() throws Exception {

        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "test.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "fake-image-content".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/products")
                        .file(imageFile)
                        .param("name", "")         // inválido
                        .param("price", "-100")    // inválido
                        .param("state", "ACTIVE")
                        .header("X-API-KEY", VALID_API_KEY))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].status").value("400"));
    }
}
