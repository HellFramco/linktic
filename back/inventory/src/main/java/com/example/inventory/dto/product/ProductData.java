package com.example.inventory.dto.product;

public class ProductData {
    private String type;
    private String id;               // viene como String, aunque es UUID
    private ProductAttributes attributes;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public ProductAttributes getAttributes() { return attributes; }
    public void setAttributes(ProductAttributes attributes) { this.attributes = attributes; }
}