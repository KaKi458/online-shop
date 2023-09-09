package com.onlineshop.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingCart {

    private List<ShoppingCartPositionDto> positions;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
}
