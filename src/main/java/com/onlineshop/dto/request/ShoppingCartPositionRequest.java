package com.onlineshop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartPositionRequest {

    private Long productId;
    private Integer quantity;
}
