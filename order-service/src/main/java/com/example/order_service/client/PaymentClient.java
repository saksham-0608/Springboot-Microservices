package com.example.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "payment-service"
)
public interface PaymentClient {
    @PostMapping("/payments/process")
    Object processPayment(
            @RequestParam("orderId") Long orderId,
            @RequestParam("amount") Double amount
    );
}
