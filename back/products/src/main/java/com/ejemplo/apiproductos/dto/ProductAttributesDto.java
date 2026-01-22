package com.ejemplo.apiproductos.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductAttributesDto {
    private String name;
    private BigDecimal price;
    private String description;
    private String state;
}