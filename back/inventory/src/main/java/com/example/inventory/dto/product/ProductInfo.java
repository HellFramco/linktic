package com.example.inventory.dto.product;   // o com.example.inventory.dto.product

import java.math.BigDecimal;
import java.util.UUID;

public class ProductInfo {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;

    // Getters obligatorios para deserialización
    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }

    // Setters opcionales (puedes quitarlos si usas record en lugar de class)
    public void setId(UUID id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}