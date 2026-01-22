package com.ejemplo.apiproductos.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductResponseWithIdDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private String description;
    private String state;
}