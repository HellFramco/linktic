package com.example.inventory.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Inventory {

    @Id
    private UUID productId;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private InventoryState state;

    public Inventory() {}

    public Inventory(UUID productId, Integer quantity, InventoryState state) {
        this.productId = productId;
        this.quantity = quantity;
        this.state = state;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public InventoryState getState() {
        return state;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public void setState(InventoryState state) {
        this.state = state;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.state = quantity > 0
                ? InventoryState.AVAILABLE
                : InventoryState.OUT_OF_STOCK;
    }
}
