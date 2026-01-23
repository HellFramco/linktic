package com.example.inventory.service;

import com.example.inventory.client.ProductClient;
import com.example.inventory.domain.Inventory;
import com.example.inventory.dto.purchase.PurchaseResult;
import com.example.inventory.exception.InsufficientInventoryException;
import com.example.inventory.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.cloud.stream.function.StreamBridge;

import java.time.Instant;
import java.util.Map;

import java.util.UUID;

@Service
public class PurchaseService {

    private final InventoryRepository inventoryRepository;
    private final ProductClient productClient;
    private final StreamBridge streamBridge;

    public PurchaseService(
            InventoryRepository inventoryRepository,
            ProductClient productClient,
            StreamBridge streamBridge
    ) {
        this.inventoryRepository = inventoryRepository;
        this.productClient = productClient;
        this.streamBridge = streamBridge;
    }

    @Transactional
    public PurchaseResult purchase(UUID productId, Integer quantity) {

        // 1️⃣ Validar que el producto existe
        productClient.getProduct(productId);

        // 2️⃣ Buscar inventory por PRODUCT ID (UUID)
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(InsufficientInventoryException::new);

        // 3️⃣ Validar stock
        if (inventory.getQuantity() < quantity) {
            throw new InsufficientInventoryException();
        }

        // 4️⃣ Descontar stock
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);

        streamBridge.send(
            "inventory-events-out-0",
            Map.of(
                "type", "PURCHASE",
                "productId", productId.toString(),
                "quantity", quantity,
                "timestamp", Instant.now().toString()
            )
        );

        // 5️⃣ Respuesta
        return new PurchaseResult(
                UUID.randomUUID(),
                productId,
                quantity
        );
    }
}
