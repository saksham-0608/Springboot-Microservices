package com.example.order_service.dto;

public class ReserveRequest {

    private Integer quantity;

    public ReserveRequest() {
    }

    public ReserveRequest(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}