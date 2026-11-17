package com.example.payment_service.controller;

import com.example.payment_service.entity.Payment;
import com.example.payment_service.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public Payment processPayment(
            @RequestParam Long orderId,
            @RequestParam Double amount) {

        return paymentService.processPayment(orderId, amount);
    }
}