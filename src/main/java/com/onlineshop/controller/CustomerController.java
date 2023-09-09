package com.onlineshop.controller;

import com.onlineshop.dto.request.ChangeQuantityRequest;
import com.onlineshop.dto.request.CustomerRequest;
import com.onlineshop.dto.request.ShoppingCartPositionRequest;
import com.onlineshop.dto.response.CustomerDto;
import com.onlineshop.dto.response.ShoppingCart;
import com.onlineshop.dto.response.ShoppingCartPositionDto;
import com.onlineshop.service.CustomerService;
import com.onlineshop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerDto createdCustomer = customerService.addCustomer(customerRequest);
        URI uri = URI.create("");
        return ResponseEntity.created(uri).body(createdCustomer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long customerId) {
        CustomerDto customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable Long customerId, @RequestBody CustomerRequest customerRequest) {
        CustomerDto updatedCustomer = customerService.updateCustomer(customerId, customerRequest);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{customerId}/shoppingCart")
    public ResponseEntity<ShoppingCartPositionDto> addPositionToShoppingCart(
            @PathVariable Long customerId, @RequestBody ShoppingCartPositionRequest positionDto) {
        ShoppingCartPositionDto createdPosition = shoppingCartService.addPosition(customerId, positionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPosition);
    }

    @GetMapping("/{customerId}/shoppingCart")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable Long customerId) {
        ShoppingCart shoppingCart  = shoppingCartService.getShoppingCart(customerId);
        return ResponseEntity.ok(shoppingCart);
    }

    @PatchMapping("/{customerId}/shoppingCart/{productId}")
    public ResponseEntity<ShoppingCartPositionDto> changeShoppingCartPositionQuantity(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestBody ChangeQuantityRequest changeQuantityRequest) {
        ShoppingCartPositionDto updatedPosition =
                shoppingCartService.changeQuantity(customerId, productId, changeQuantityRequest);
        return ResponseEntity.ok(updatedPosition);
    }

    @DeleteMapping("/{customerId}/shoppingCart/{productId}")
    public ResponseEntity<Void> deleteShoppingCartPosition(
            @PathVariable Long customerId, @PathVariable Long productId) {
        shoppingCartService.deletePosition(customerId, productId);
        return ResponseEntity.noContent().build();
    }
 }
