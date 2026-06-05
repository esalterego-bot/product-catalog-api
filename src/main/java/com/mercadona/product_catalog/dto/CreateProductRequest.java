package com.mercadona.product_catalog.dto;

import com.mercadona.product_catalog.enums.ProductCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CreateProductRequest {
    @NotBlank
    private String name;

    @NotNull
    private ProductCategory category;

    @Positive
    @NotNull
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    private Integer stock;
}
