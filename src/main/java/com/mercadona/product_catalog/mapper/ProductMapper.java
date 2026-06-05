package com.mercadona.product_catalog.mapper;

import com.mercadona.product_catalog.dto.CreateProductRequest;
import com.mercadona.product_catalog.dto.ProductResponse;
import com.mercadona.product_catalog.entity.Product;

public class ProductMapper {
    public static Product toEntity(CreateProductRequest request) {

        Product product = new Product();

        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return product;
    }

    public static ProductResponse toResponse(Product product) {

        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setCategory(product.getCategory());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        return response;
    }
}
