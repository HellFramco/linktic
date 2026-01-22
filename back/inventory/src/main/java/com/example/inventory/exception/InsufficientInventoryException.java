package com.example.inventory.exception;

public class InsufficientInventoryException extends BusinessException {

    public InsufficientInventoryException() {
        super("Insufficient inventory");
    }
}
