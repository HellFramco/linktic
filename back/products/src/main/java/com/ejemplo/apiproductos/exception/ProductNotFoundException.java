package com.ejemplo.apiproductos.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    
    public ProductNotFoundException(String message) {
        super(message);
    }
    
    public ProductNotFoundException(UUID id) {
        super("Producto con id " + id + " no encontrado");
    }
}