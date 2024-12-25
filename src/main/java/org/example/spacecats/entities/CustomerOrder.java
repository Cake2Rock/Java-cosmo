package org.example.spacecats.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class CustomerOrder {
    private UUID orderId;
    private LocalDateTime orderPlacedOn;
    private OrderState orderState;
    private List<Product> purchasedItems;

    public enum OrderState {
        PENDING,
        DISPATCHED,
        RECEIVED,
        CANCELLED
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderPlacedOn() {
        return orderPlacedOn;
    }

    public void setOrderPlacedOn(LocalDateTime orderPlacedOn) {
        this.orderPlacedOn = orderPlacedOn;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public List<Product> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<Product> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }
}
