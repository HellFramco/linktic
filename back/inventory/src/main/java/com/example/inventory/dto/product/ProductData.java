package com.example.inventory.dto.product;

import com.fasterxml.jackson.databind.JsonNode;

public class ProductData {
    private String type;
    private String id;
    private JsonNode attributes;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public JsonNode getAttributes() { return attributes; }
    public void setAttributes(JsonNode attributes) { this.attributes = attributes; }
}
