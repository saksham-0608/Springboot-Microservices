
package com.example.order_service.service;

import com.example.order_service.client.InventoryClient;
import com.example.order_service.dto.OrderRequest;
import com.example.order_service.dto.ReserveRequest;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderStatus;
import com.example.order_service.repository.OrderRepository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final PaymentCommunicationService paymentCommunicationService;

    public OrderService(
            OrderRepository orderRepository,
            InventoryClient inventoryClient,
            PaymentCommunicationService paymentCommunicationService) {

        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
        this.paymentCommunicationService = paymentCommunicationService;
    }

    public Order placeOrder(String idempotencyKey, OrderRequest request) {

        // Step 1: Check existing request
        Optional<Order> existingOrder =
                orderRepository.findByIdempotencyKey(idempotencyKey);

        if (existingOrder.isPresent()) {
            return existingOrder.get();
        }

        // Step 2: Create PENDING order
        Order order = new Order();

        order.setIdempotencyKey(idempotencyKey);
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setAmount(request.getAmount());
        order.setStatus(OrderStatus.PENDING);

        // Step 3: Save order with duplicate protection
        try {
            order = orderRepository.saveAndFlush(order);

        } catch (DataIntegrityViolationException e) {

            Optional<Order> duplicateOrder =
                    orderRepository.findByIdempotencyKey(idempotencyKey);

            if (duplicateOrder.isPresent()) {
                return duplicateOrder.get();
            }

            throw e;
        }

        // Track whether inventory was successfully reserved
        boolean inventoryReserved = false;

        try {

            // Step 4: Reserve inventory
            inventoryClient.reserveProduct(
                    order.getProductId(),
                    new ReserveRequest(order.getQuantity())
            );

            inventoryReserved = true;

            // Step 5: Process payment
            paymentCommunicationService.processPayment(
                    order.getId(),
                    order.getAmount().doubleValue()
            );

            // Step 6: Confirm order
            order.setStatus(OrderStatus.CONFIRMED);

            return orderRepository.save(order);

        } catch (Exception e) {

            // Step 7: Release inventory only if reservation succeeded
            if (inventoryReserved) {
                try {
                    inventoryClient.releaseProduct(
                            order.getProductId(),
                            new ReserveRequest(order.getQuantity())
                    );

                } catch (Exception compensationError) {
                    e.addSuppressed(compensationError);
                }
            }

            // Step 8: Cancel order
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            throw new RuntimeException(
                    "Order failed. Order cancelled.", e
            );
        }
    }
}
