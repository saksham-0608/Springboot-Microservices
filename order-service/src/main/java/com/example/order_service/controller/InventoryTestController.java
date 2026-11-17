package com.example.order_service.controller;

import com.example.order_service.client.InventoryClient;
import com.example.order_service.dto.ReserveRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class InventoryTestController {

    private final InventoryClient inventoryClient;

    public InventoryTestController(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    @PostMapping("/reserve/{id}")
    public Object reserveProduct(
            @PathVariable Long id,
            @RequestParam Integer quantity) {

        ReserveRequest request = new ReserveRequest(quantity);

        return inventoryClient.reserveProduct(id, request);
    }
}