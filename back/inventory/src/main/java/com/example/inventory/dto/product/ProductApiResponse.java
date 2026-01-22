package com.example.inventory.dto.product;

import java.util.UUID;

// Nivel más externo
public class ProductApiResponse {
    private ProductData data;

    public ProductData getData() {
        return data;
    }

    public void setData(ProductData data) {
        this.data = data;
    }
}