package com.example.payment_service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private Double amount;

    private String status;

    public Payment() {
    }

    public Payment(Long orderId, Double amount, String status) {
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
    }

    // Generate getters and setters
}