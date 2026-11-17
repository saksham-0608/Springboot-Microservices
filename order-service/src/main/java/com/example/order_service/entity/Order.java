package com.example.order_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import jakarta.persistence.Column;
@Entity
@Table(name = "customer_orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;
    private Integer quantity;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {
    }

    // Generate getters and setters
}