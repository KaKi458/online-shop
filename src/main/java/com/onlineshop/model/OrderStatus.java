package com.onlineshop.model;

import jakarta.persistence.Entity;
import lombok.*;

public enum OrderStatus {

    PENDING,
    AWAITING_PAYMENT,
    PAID,
    IN_PREPARATION,
    SHIPPED,
    DELIVERED,
    REFUNDED,
    CANCELLED

}
