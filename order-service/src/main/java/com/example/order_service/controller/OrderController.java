package com.example.order_service.controller;
import org.springframework.web.bind.annotation.RequestHeader;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.entity.Order;
import com.example.order_service.service.OrderService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order placeOrder(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody OrderRequest request) {

        return orderService.placeOrder(idempotencyKey, request);
    }}