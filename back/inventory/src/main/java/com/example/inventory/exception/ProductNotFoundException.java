package com.example.inventory.exception;

import java.util.UUID;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(UUID id) {
        super("Product with id " + id + " does not exist");
    }
}
