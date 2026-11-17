package com.example.inventory_service.dto;

public class ReserveRequest {
    private Integer quantity;

    public ReserveRequest() {
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
