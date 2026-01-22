package com.example.inventory.dto.purchase;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PurchaseRequest {

    @NotNull
    private UUID productId;

    @NotNull
    @Min(1)
    private Integer quantity;

    public UUID getProductId() {
        return productId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }


    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
