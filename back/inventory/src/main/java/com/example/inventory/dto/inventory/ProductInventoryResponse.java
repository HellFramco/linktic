package com.example.inventory.dto.inventory;   // o com.example.inventory.dto.inventory si prefieres

import com.example.inventory.domain.InventoryState;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductInventoryResponse {

    private UUID productId;
    private String name;
    private String description;     // opcional
    private BigDecimal price;
    private String imageUrl;        // opcional
    private Integer quantity;
    private InventoryState state;

    // Constructor completo
    public ProductInventoryResponse(
            UUID productId,
            String name,
            String description,
            BigDecimal price,
            String imageUrl,
            Integer quantity,
            InventoryState state) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.state = state;
    }

    // Getters (necesarios para Jackson)
    public UUID getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
    public Integer getQuantity() { return quantity; }
    public InventoryState getState() { return state; }
}