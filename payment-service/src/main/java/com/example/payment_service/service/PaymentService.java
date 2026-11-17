package com.example.payment_service.service;

import com.example.payment_service.entity.Payment;
import com.example.payment_service.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

        public Payment processPayment(Long orderId, Double amount) {


            if (amount > 100000) {
                throw new RuntimeException("Payment declined");
            }

            Payment payment = new Payment(orderId, amount, "SUCCESS");

            return paymentRepository.save(payment);
        }
}