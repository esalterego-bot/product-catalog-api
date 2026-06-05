package com.mercadona.product_catalog.service;

import com.mercadona.product_catalog.dto.CreateProductRequest;
import com.mercadona.product_catalog.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(CreateProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    void deleteProduct(Long id);

    ProductResponse updateProduct(
            Long id,
            CreateProductRequest request
    );
}
