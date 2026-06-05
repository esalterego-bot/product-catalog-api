package com.mercadona.product_catalog.controller;

import com.mercadona.product_catalog.entity.Product;
import com.mercadona.product_catalog.enums.ProductCategory;
import com.mercadona.product_catalog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void cleanDatabase() {
        productRepository.deleteAll();
    }
    @Test
    void shouldReturnProducts() throws Exception {

        mockMvc.perform(
                        get("/products")
                                .with(httpBasic("javier", "admin123"))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnProductById() throws Exception {

        Product product = new Product();
        product.setName("Coca Cola");
        product.setCategory(ProductCategory.DRINKS);
        product.setPrice(BigDecimal.valueOf(1.50));
        product.setStock(100);

        Product savedProduct = productRepository.save(product);

        mockMvc.perform(
                        get("/products/" + savedProduct.getId())
                                .with(httpBasic("javier", "admin123"))
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenProductDoesNotExist() throws Exception {

        mockMvc.perform(
                        get("/products/999")
                                .with(httpBasic("javier", "admin123"))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnUnauthorizedWhenCredentialsAreMissing() throws Exception {

        mockMvc.perform(get("/products"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldCreateProduct() throws Exception {

        String requestBody = """
                        {
                          "name": "Fanta Test 123",
                          "category": "DRINKS",
                          "price": 2.50,
                          "stock": 50
                        }
                        """;

        mockMvc.perform(
                        post("/products")
                                .with(httpBasic("javier", "admin123"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnConflictWhenProductAlreadyExists() throws Exception {

        String requestBody = """
            {
              "name": "Coca Cola",
              "category": "DRINKS",
              "price": 1.50,
              "stock": 100
            }
            """;

        mockMvc.perform(
                post("/products")
                        .with(httpBasic("javier", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
        );

        mockMvc.perform(
                        post("/products")
                                .with(httpBasic("javier", "admin123"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnBadRequestForInvalidProduct() throws Exception {

        String requestBody = """
            {
              "name": "",
              "category": "DRINKS",
              "price": -10,
              "stock": -5
            }
            """;

        mockMvc.perform(
                        post("/products")
                                .with(httpBasic("javier", "admin123"))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )
                .andExpect(status().isBadRequest());
    }
}
