package com.ejemplo.apiproductos.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.ejemplo.apiproductos.util.ProductState;

public class ProductResponseDto {

    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
    private ProductState state;
    private String imageUrl;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
