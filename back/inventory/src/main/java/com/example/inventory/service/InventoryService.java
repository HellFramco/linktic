package com.example.inventory.service;

import com.example.inventory.domain.Inventory;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public Inventory getInventory(UUID productId) {
        return repository.findById(productId).orElse(null);
    }

    public Inventory save(Inventory inventory) {
        return repository.save(inventory);
    }
}
