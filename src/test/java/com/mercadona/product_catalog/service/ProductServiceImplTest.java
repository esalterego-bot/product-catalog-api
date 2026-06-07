package com.mercadona.product_catalog.service;

import com.mercadona.product_catalog.dto.CreateProductRequest;
import com.mercadona.product_catalog.dto.ProductResponse;
import com.mercadona.product_catalog.entity.Product;
import com.mercadona.product_catalog.enums.ProductCategory;
import com.mercadona.product_catalog.exception.ProductAlreadyExistsException;
import com.mercadona.product_catalog.exception.ProductNotFoundException;
import com.mercadona.product_catalog.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void cleanDatabase() {
        productRepository.deleteAll();
    }
    @InjectMocks
    private ProductServiceImpl productService;

    public ProductServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldThrowExceptionWhenProductAlreadyExists() {

        CreateProductRequest request = createRequest();

        when(productRepository.existsByNameAndCategory(
                request.getName(),
                request.getCategory()))
                .thenReturn(true);

        assertThrows(
                ProductAlreadyExistsException.class,
                () -> productService.createProduct(request)
        );
    }

    @Test
    void shouldCreateProductSuccessfully() {

        CreateProductRequest request = createRequest();
        Product savedProduct = createProduct();

        when(productRepository.existsByNameAndCategory(
                request.getName(),
                request.getCategory()))
                .thenReturn(false);

        when(productRepository.save(any(Product.class)))
                .thenReturn(savedProduct);

        ProductResponse response =
                productService.createProduct(request);

        assertEquals(1L, response.getId());
        assertEquals("Coca Cola", response.getName());
        assertEquals(ProductCategory.DRINKS, response.getCategory());
        assertEquals(BigDecimal.valueOf(1.50), response.getPrice());
        assertEquals(100, response.getStock());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldGetProductByIdSuccessfully() {

        Product product = createProduct();

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse response =
                productService.getProductById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Coca Cola", response.getName());
        assertEquals(ProductCategory.DRINKS, response.getCategory());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getProductById(1L)
        );

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldDeleteProductSuccessfully() {

        when(productRepository.existsById(1L))
                .thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingProduct() {

        when(productRepository.existsById(1L))
                .thenReturn(false);

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.deleteProduct(1L)
        );
    }

    @Test
    void shouldUpdateProductSuccessfully() {

        CreateProductRequest request = createRequest();
        request.setName("Pepsi Max");

        Product product = createProduct();
        product.setName("Pepsi Max");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(createProduct()));

        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponse response =
                productService.updateProduct(1L, request);

        assertEquals("Pepsi Max", response.getName());

        verify(productRepository).findById(1L);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.updateProduct(
                        1L,
                        createRequest()
                )
        );
    }

    private CreateProductRequest createRequest() {

        CreateProductRequest request = new CreateProductRequest();
        request.setName("Coca Cola");
        request.setCategory(ProductCategory.DRINKS);
        request.setPrice(BigDecimal.valueOf(1.50));
        request.setStock(100);

        return request;
    }

    private Product createProduct() {

        Product product = new Product();
        product.setId(1L);
        product.setName("Coca Cola");
        product.setCategory(ProductCategory.DRINKS);
        product.setPrice(BigDecimal.valueOf(1.50));
        product.setStock(100);

        return product;
    }
}