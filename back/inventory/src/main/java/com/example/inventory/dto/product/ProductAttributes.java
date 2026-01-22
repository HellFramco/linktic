package com.example.inventory.dto.product;

import java.math.BigDecimal;

public class ProductAttributes {
    private String id;             // repetido, pero viene así
    private String name;
    private BigDecimal price;
    private String description;
    private String state;
    private String imageUrl;

    // Getters obligatorios
    public String getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public String getDescription() { return description; }
    public String getState() { return state; }
    public String getImageUrl() { return imageUrl; }

    // Setters (necesarios para Jackson)
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setState(String state) { this.state = state; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}