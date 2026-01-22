package com.ejemplo.apiproductos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private BigDecimal price;
    private String description;
    private String state;

    @Column(name = "image_url")
    private String imageUrl;
}