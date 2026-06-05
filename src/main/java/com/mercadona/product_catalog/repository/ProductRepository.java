package com.mercadona.product_catalog.repository;

import com.mercadona.product_catalog.entity.Product;
import com.mercadona.product_catalog.enums.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByNameAndCategory(
            String name,
            ProductCategory category
    );
}
