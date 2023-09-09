package com.onlineshop.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long productId;
    private String name;
    private Long categoryId;
    private String categoryName;
    private BigDecimal price;
    private Integer quantity;
}
