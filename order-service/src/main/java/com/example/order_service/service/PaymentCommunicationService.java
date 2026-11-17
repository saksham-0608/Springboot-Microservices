package com.example.order_service.service;

import com.example.order_service.client.PaymentClient;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;



@Service
public class PaymentCommunicationService {

    private final PaymentClient paymentClient;

    public PaymentCommunicationService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @Retry(name = "paymentRetry")
    @CircuitBreaker(
            name = "paymentService",
            fallbackMethod = "paymentFallback"
    )
    public Object processPayment(Long orderId, Double amount) {

        return paymentClient.processPayment(orderId, amount);
    }

    public Object paymentFallback(
            Long orderId,
            Double amount,
            Throwable ex) {

        throw new RuntimeException(
                "Payment Service is unavailable. Circuit Breaker activated.",
                ex
        );
    }
}