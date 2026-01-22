package com.example.inventory.controller;

import com.example.inventory.domain.Inventory;
import com.example.inventory.domain.InventoryState;
import com.example.inventory.dto.inventory.ProductInventoryResponse;
import com.example.inventory.dto.product.ProductApiResponse;
import com.example.inventory.dto.product.ProductAttributes;
import com.example.inventory.repository.InventoryRepository;
import com.example.inventory.dto.inventory.UpdateInventoryRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;


import java.util.UUID;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;
    private final RestClient productServiceClient;

    public InventoryController(
            InventoryRepository inventoryRepository,
            RestClient productServiceClient) {
        this.inventoryRepository = inventoryRepository;
        this.productServiceClient = productServiceClient;
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductInventoryResponse> getProductWithInventory(@PathVariable UUID productId) {

        // 1. Inventario
        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);
        int quantity = (inventory != null) ? inventory.getQuantity() : 0;
        InventoryState state = (inventory != null) ? inventory.getState() : InventoryState.OUT_OF_STOCK;

        // 2. Llamada al servicio de productos
        ProductApiResponse apiResponse;
        try {
            apiResponse = productServiceClient.get()
                    .uri("/products/{id}", productId)
                    .header("X-API-KEY", "producto-api-linktic")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        if (res.getStatusCode() == HttpStatus.NOT_FOUND) {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
                        }
                        throw new ResponseStatusException(res.getStatusCode(), "Error en la solicitud al servicio de productos");
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) ->
                            new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Error interno en el servicio de productos"))
                    .body(ProductApiResponse.class);

        } catch (RestClientException e) {
            // Solo para errores no manejados por .onStatus (ej: timeout, conexión fallida, etc.)
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Falla al conectar con el servicio de productos: " + e.getMessage(), e);
        }

        // 3. Validar respuesta
        if (apiResponse == null || apiResponse.getData() == null || apiResponse.getData().getAttributes() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Estructura de producto inválida");
        }

        ProductAttributes attrs = apiResponse.getData().getAttributes();

        // 4. Respuesta combinada
        ProductInventoryResponse response = new ProductInventoryResponse(
                productId,
                attrs.getName(),
                attrs.getDescription(),
                attrs.getPrice(),
                attrs.getImageUrl(),
                quantity,
                state
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductInventoryResponse> updateInventory(
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateInventoryRequest request) {

        // 1. Buscar o crear el registro de inventario
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> new Inventory(productId, 0, InventoryState.OUT_OF_STOCK));

        // 2. Actualizar cantidad y estado
        int newQuantity = request.getQuantity();
        if (newQuantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad no puede ser negativa");
        }

        inventory.setQuantity(newQuantity); // Este método ya actualiza el estado automáticamente (por tu lógica en la entidad)

        inventoryRepository.save(inventory);

        // 3. Obtener datos del producto (igual que en GET)
        ProductApiResponse apiResponse;
        try {
            apiResponse = productServiceClient.get()
                    .uri("/products/{id}", productId)
                    .header("X-API-KEY", "producto-api-linktic")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        if (res.getStatusCode() == HttpStatus.NOT_FOUND) {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado");
                        }
                        throw new ResponseStatusException(res.getStatusCode(), "Error al consultar producto");
                    })
                    .body(ProductApiResponse.class);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "No se pudo obtener información del producto");
        }

        if (apiResponse == null || apiResponse.getData() == null || apiResponse.getData().getAttributes() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado o respuesta inválida");
        }

        ProductAttributes attrs = apiResponse.getData().getAttributes();

        // 4. Respuesta completa
        ProductInventoryResponse response = new ProductInventoryResponse(
                productId,
                attrs.getName(),
                attrs.getDescription(),
                attrs.getPrice(),
                attrs.getImageUrl(),
                inventory.getQuantity(),
                inventory.getState()
        );

        return ResponseEntity.ok(response);
    }
}