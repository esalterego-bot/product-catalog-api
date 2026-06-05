package com.mercadona.product_catalog.dto;

import com.mercadona.product_catalog.enums.ProductCategory;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private ProductCategory category;
    private BigDecimal price;
    private Integer stock;
}
