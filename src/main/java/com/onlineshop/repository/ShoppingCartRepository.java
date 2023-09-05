package com.onlineshop.repository;

import com.onlineshop.model.ShoppingCartPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartPosition, Long> {

    List<ShoppingCartPosition> findAllByCustomerId(Long customerId);
}
