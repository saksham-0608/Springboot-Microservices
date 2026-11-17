package com.example.order_service.client;


import com.example.order_service.dto.ReserveRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping("/products/{id}/reserve")
    Object reserveProduct(
            @PathVariable("id") Long id,
            @RequestBody ReserveRequest request
    );

    @PostMapping("/products/{id}/release")
    Object releaseProduct(
            @PathVariable("id") Long id,
            @RequestBody ReserveRequest request
    );
}