package com.ejemplo.apiproductos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import com.ejemplo.apiproductos.util.ProductState;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    @NotBlank
    private String name;

    @PositiveOrZero
    private BigDecimal price;

    private String description;

    @NotNull
    private ProductState state;

    @Schema(type = "string", format = "binary", description = "Imagen del producto")
    private MultipartFile image;

    // getters y setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public ProductState getState() { return state; }
    public void setState(ProductState state) { this.state = state; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }
}
