package com.example.inventory.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> notFound(ProductNotFoundException ex) {
        return ResponseEntity.status(404).body(error("404", "Product not found", ex.getMessage()));
    }

    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<?> conflict(InsufficientInventoryException ex) {
        return ResponseEntity.status(409).body(error("409", "Insufficient inventory", ex.getMessage()));
    }

    private Map<String, Object> error(String status, String title, String detail) {
        return Map.of(
                "errors", new Object[]{
                        Map.of(
                                "status", status,
                                "title", title,
                                "detail", detail
                        )
                }
        );
    }
}
