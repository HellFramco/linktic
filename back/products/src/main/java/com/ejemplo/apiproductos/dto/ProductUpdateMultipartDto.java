package com.ejemplo.apiproductos.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;
import com.ejemplo.apiproductos.util.ProductState;

public class ProductUpdateMultipartDto {

    private String name;
    private BigDecimal price;
    private String description;
    private ProductState state;
    private MultipartFile image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductState getState() {
        return state;
    }

    public void setState(ProductState state) {
        this.state = state;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
