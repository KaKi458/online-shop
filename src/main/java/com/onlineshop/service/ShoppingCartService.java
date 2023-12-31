package com.onlineshop.service;

import com.onlineshop.dto.request.ChangeQuantityRequest;
import com.onlineshop.dto.request.ShoppingCartPositionRequest;
import com.onlineshop.dto.response.ShoppingCart;
import com.onlineshop.dto.response.ShoppingCartPositionDto;
import com.onlineshop.exception.ApiException;
import com.onlineshop.model.Customer;
import com.onlineshop.model.Product;
import com.onlineshop.model.ShoppingCartPosition;
import com.onlineshop.repository.CustomerRepository;
import com.onlineshop.repository.ProductRepository;
import com.onlineshop.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartPositionDto addPosition(Long customerId, ShoppingCartPositionRequest positionDto) {
        Customer customer = findCustomer(customerId);
        Product product = findProduct(positionDto.getProductId());
        Integer quantity = positionDto.getQuantity();
        ShoppingCartPosition position;
        if (product.getQuantity() < quantity) {
            throw new ApiException(
                    HttpStatus.CONFLICT, "The available product quantity is too low."
            );
        }

        Optional<ShoppingCartPosition> existingPositionOptional = customer.getShoppingCart()
                .stream()
                .filter(p->p.getProduct().getId() == (long) product.getId())
                .findAny();

        if (existingPositionOptional.isPresent()) {
            ShoppingCartPosition existingPosition = existingPositionOptional.get();
            Integer existingQuantity = existingPosition.getQuantity();
            existingPosition.setQuantity(existingQuantity + positionDto.getQuantity());
            position = existingPosition;
        } else {
            position = ShoppingCartPosition.builder()
                    .customer(customer)
                    .product(product)
                    .quantity(quantity)
                    .build();

            customer.getShoppingCart().add(position);
        }

        ShoppingCartPosition savedPosition = shoppingCartRepository.save(position);
        return mapToDto(savedPosition);
    }

    public ShoppingCartPositionDto changeQuantity(
            Long customerId, Long productId, ChangeQuantityRequest changeQuantityRequest) {
        Customer customer = findCustomer(customerId);
        Integer quantity = changeQuantityRequest.getQuantity();

        ShoppingCartPosition position = customer.getShoppingCart()
                .stream()
                .filter(p->p.getProduct().getId() == (long) productId)
                .findAny()
                .orElseThrow(() -> new ApiException(
                        HttpStatus.BAD_REQUEST,
                        "There is no product with id <" + productId + "> in the shopping cart."
                ));

        if (position.getProduct().getQuantity() < quantity) {
            throw new ApiException(HttpStatus.CONFLICT, "The available product quantity is too low.");
        }
        position.setQuantity(quantity);
        ShoppingCartPosition updatedPosition = shoppingCartRepository.save(position);
        return mapToDto(updatedPosition);
    }

    public ShoppingCart getShoppingCart(Long customerId) {
        List<ShoppingCartPosition> positionList = shoppingCartRepository.findAllByCustomerId(customerId);
        List<ShoppingCartPositionDto> positionDtoList = positionList.stream().map(this::mapToDto).toList();
        int totalQuantity = positionList.stream().mapToInt(ShoppingCartPosition::getQuantity).sum();
        BigDecimal totalPrice = BigDecimal.valueOf(
                positionDtoList.stream()
                        .map(ShoppingCartPositionDto::getTotalPrice)
                        .mapToDouble(BigDecimal::doubleValue).sum());
        return ShoppingCart.builder()
                .positions(positionDtoList)
                .totalQuantity(totalQuantity)
                .totalPrice(totalPrice)
                .build();
    }

    public void deletePosition(Long customerId, Long productId) {
        Customer customer = findCustomer(customerId);
        ShoppingCartPosition position = customer.getShoppingCart()
                .stream()
                .filter(p->p.getProduct().getId() == (long) productId)
                .findAny()
                .orElseThrow(() -> new ApiException(
                        HttpStatus.BAD_REQUEST, "brak produktu w koszyku"));
        shoppingCartRepository.delete(position);
    }

    private ShoppingCartPositionDto mapToDto(ShoppingCartPosition position) {
        Product product = position.getProduct();
        int quantity = position.getQuantity();

        return ShoppingCartPositionDto.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .quantity(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .build();
    }

    private Customer findCustomer(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Customer with id <" + customerId + "> does not exist."
                ));
    }

    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Product with id <" + productId + "> does not exist."
                ));
    }

}
