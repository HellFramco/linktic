package com.example.inventory.dto.purchase;

import java.util.UUID;

public class PurchaseResult {

    private UUID purchaseId;
    private UUID productId;
    private Integer quantity;

    public PurchaseResult(UUID purchaseId, UUID productId, Integer quantity) {
        this.purchaseId = purchaseId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getPurchaseId() {
        return purchaseId;
    }

    public UUID getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
