package com.onlineshop.controller;

import com.onlineshop.dto.ChangeQuantityDto;
import com.onlineshop.dto.CustomerDto;
import com.onlineshop.dto.ShoppingCartPositionDto;
import com.onlineshop.service.CustomerService;
import com.onlineshop.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto createdCustomer = customerService.addCustomer(customerDto);
        URI uri = URI.create("");
        return ResponseEntity.created(uri).body(createdCustomer);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long customerId) {
        CustomerDto customerDto = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customerDto);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(
            @PathVariable Long customerId, @RequestBody CustomerDto customerDto) {
        CustomerDto updatedCustomerDto = customerService.updateCustomer(customerId, customerDto);
        return ResponseEntity.ok(updatedCustomerDto);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{customerId}/shoppingCart")
    public ResponseEntity<ShoppingCartPositionDto> addPositionToShoppingCart(
            @PathVariable Long customerId, @RequestBody ShoppingCartPositionDto positionDto) {
        ShoppingCartPositionDto createdPositionDto = shoppingCartService.addPosition(customerId, positionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPositionDto);
    }

    @GetMapping("/{customerId}/shoppingCart")
    public ResponseEntity<List<ShoppingCartPositionDto>> getShoppingCart(@PathVariable Long customerId) {
        List<ShoppingCartPositionDto> shoppingCart  = shoppingCartService.getShoppingCart(customerId);
        return ResponseEntity.ok(shoppingCart);
    }

    @PatchMapping("/{customerId}/shoppingCart/{productId}")
    public ResponseEntity<ShoppingCartPositionDto> changeShoppingCartPositionQuantity(
            @PathVariable Long customerId,
            @PathVariable Long productId,
            @RequestBody ChangeQuantityDto changeQuantityDto) {
        ShoppingCartPositionDto updatedPositionDto =
                shoppingCartService.changeQuantity(customerId, productId, changeQuantityDto);
        return ResponseEntity.ok(updatedPositionDto);
    }

    @DeleteMapping("/{customerId}/shoppingCart/{productId}")
    public ResponseEntity<Void> deleteShoppingCartPosition(
            @PathVariable Long customerId, @PathVariable Long productId) {
        shoppingCartService.deletePosition(customerId, productId);
        return ResponseEntity.noContent().build();
    }
 }
