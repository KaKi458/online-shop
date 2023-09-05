package com.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartPositionDto {

    private Long productId;
    private Integer quantity;
}
