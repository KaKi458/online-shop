package com.onlineshop.controller;

import com.onlineshop.dto.OrderDto;
import com.onlineshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> addOrder(@RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.addOrder(orderDto);
        URI uri = URI.create("");
        return ResponseEntity.created(uri).body(createdOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String sortBy,
            @RequestParam String sortDir) {
        List<OrderDto> orderDtoList = orderService.getOrders(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(orderDtoList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        OrderDto orderDto = orderService.getOrder(orderId);
        return ResponseEntity.ok(orderDto);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long orderId, @RequestBody OrderDto orderDto) {
        OrderDto updatedOrderDto = orderService.updateOrder(orderId, orderDto);
        return ResponseEntity.ok(updatedOrderDto);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
