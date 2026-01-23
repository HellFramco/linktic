package com.ejemplo.apiproductos.dto;
import com.ejemplo.apiproductos.util.ProductState;

import java.math.BigDecimal;

public class ProductUpdateDto {

    private String name;
    private BigDecimal price;
    private String description;
    private ProductState state;

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
}
