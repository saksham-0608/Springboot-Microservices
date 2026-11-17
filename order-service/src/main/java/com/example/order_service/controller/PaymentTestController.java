package com.example.order_service.controller;

import com.example.order_service.client.PaymentClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class PaymentTestController {

    private final PaymentClient paymentClient;

    public PaymentTestController(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @PostMapping("/payment")
    public Object processPayment(
            @RequestParam Long orderId,
            @RequestParam Double amount) {

        return paymentClient.processPayment(orderId, amount);
    }
}