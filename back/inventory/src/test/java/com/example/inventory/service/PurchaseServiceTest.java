package com.example.inventory.service;

import com.example.inventory.client.ProductClient;
import com.example.inventory.domain.Inventory;
import com.example.inventory.domain.InventoryState;
import com.example.inventory.dto.purchase.PurchaseResult;
import com.example.inventory.repository.InventoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private PurchaseService purchaseService;

    @Test
    void should_purchase_successfully_when_stock_is_available() {
        UUID productId = UUID.randomUUID();
        int quantity = 2;

        Inventory inventory = new Inventory(
                productId,
                10,
                InventoryState.AVAILABLE   // ← usa el real
        );

        when(productClient.getProduct(productId)).thenReturn(null);
        when(inventoryRepository.findById(productId))
                .thenReturn(Optional.of(inventory));
        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(inventory);

        PurchaseResult result = purchaseService.purchase(productId, quantity);

        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals(quantity, result.getQuantity());
        assertEquals(8, inventory.getQuantity());

        verify(productClient).getProduct(productId);
        verify(inventoryRepository).save(inventory);
    }
}
