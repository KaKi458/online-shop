package com.onlineshop.service;

import com.onlineshop.dto.response.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    public OrderDto addOrder(OrderDto orderDto) {
        return null;
    }

    public OrderDto updateOrder(long orderId, OrderDto orderDto) {
        return null;

    }

    public List<OrderDto> getOrders(int pageNo, int pageSize, String sortBy, String sortDir) {
        return null;

    }

    public OrderDto getOrder(long orderId) {
        return null;

    }

    public void deleteOrder(long orderId) {

    }


}
