package com.example.inventory.dto.product;

import java.util.UUID;

/**
 * DTO externo (Anti-Corruption Layer)
 * Representa SOLO lo que inventory necesita del Product Service
 */
public class ProductResponse {

    private UUID id;
    private String name;
    private Boolean active;

    public ProductResponse() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
