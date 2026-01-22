package com.ejemplo.apiproductos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    @NotBlank
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    private String description;

    @NotBlank
    private String state;

    @Schema(type = "string", format = "binary", description = "Imagen del producto")
    private MultipartFile image;
}
