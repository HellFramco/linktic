package com.example.inventory.events;

import com.example.inventory.domain.Inventory;
import com.example.inventory.domain.InventoryState;
import com.example.inventory.repository.InventoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.UUID;

@Configuration
public class InventoryEventListener {

    private final InventoryRepository repository;

    public InventoryEventListener(InventoryRepository repository) {
        this.repository = repository;
    }

    @Bean
    public Consumer<ProductCreatedEvent> handleProductCreated() {
        return event -> {
            try {
                UUID productId = event.getId();
                System.out.println("Evento ProductCreated recibido - ID: " + productId);

                if (productId == null) {
                    System.out.println("ERROR: productId es null en el evento!");
                    return;
                }

                Inventory inventory = new Inventory(
                        productId,
                        0,
                        InventoryState.OUT_OF_STOCK
                );

                repository.save(inventory);
                System.out.println("Inventario creado exitosamente para productId: " + productId);
            } catch (Exception e) {
                System.err.println("Error al procesar evento: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }
}
