package com.ejemplo.apiproductos.entity;

import jakarta.persistence.*;
import lombok.Data;

import com.ejemplo.apiproductos.util.ProductState;
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

    @Enumerated(EnumType.STRING)
    private ProductState state;

    @Column(name = "image_url")
    private String imageUrl;
}
