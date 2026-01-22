package com.example.inventory.dto.inventory;

import com.example.inventory.domain.InventoryState;
import java.util.UUID;

public class InventoryResponse {

    private UUID productId;
    private Integer quantity;
    private InventoryState state;

    public InventoryResponse(UUID productId, Integer quantity, InventoryState state) {
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
}
