package com.example.inventory.controller;

import com.example.inventory.domain.Inventory;
import com.example.inventory.domain.InventoryState;
import com.example.inventory.dto.inventory.ProductInventoryResponse;
import com.example.inventory.dto.product.PagedProductAttributes;
import com.example.inventory.dto.product.ProductApiResponse;
import com.example.inventory.dto.product.ProductAttributes;
import com.example.inventory.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.inventory.dto.inventory.UpdateInventoryRequest;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.data.domain.Page;

import java.util.UUID;
import java.util.List;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;
    private final RestClient productServiceClient;
    private final ObjectMapper mapper = new ObjectMapper();  // <-- AQUÍ

    public InventoryController(InventoryRepository inventoryRepository, RestClient productServiceClient) {
        this.inventoryRepository = inventoryRepository;
        this.productServiceClient = productServiceClient;
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductInventoryResponse>> getAllActiveProductsWithInventory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        String uri = UriComponentsBuilder.fromPath("/products")
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sortBy", sortBy)
                .queryParam("direction", direction)
                .build()
                .toUriString();

        ProductApiResponse apiResponse;

        try {
            apiResponse = productServiceClient.get()
                    .uri(uri)
                    .header("X-API-KEY", "producto-api-linktic")
                    .retrieve()
                    .body(ProductApiResponse.class);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Falla al conectar con el servicio de productos: " + e.getMessage(), e);
        }

        if (apiResponse == null || apiResponse.getData() == null || apiResponse.getData().getAttributes() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Respuesta inválida del servicio de productos");
        }

        // Convertir JsonNode -> PagedProductAttributes
        PagedProductAttributes pagedAttributes = new ObjectMapper()
                .convertValue(apiResponse.getData().getAttributes(), PagedProductAttributes.class);

        if (pagedAttributes == null || pagedAttributes.getContent() == null) {
            return ResponseEntity.ok(Page.empty());
        }

        List<ProductAttributes> activeProducts = pagedAttributes.getContent().stream()
                .filter(p -> "0".equals(p.getState()))
                .toList();

        List<ProductInventoryResponse> responses = activeProducts.stream()
                .map(attrs -> {
                    UUID productId = UUID.fromString(attrs.getId());
                    Inventory inv = inventoryRepository.findByProductId(productId)
                            .orElseGet(() -> new Inventory(productId, 0, InventoryState.OUT_OF_STOCK));

                    return new ProductInventoryResponse(
                            productId,
                            attrs.getName(),
                            attrs.getDescription(),
                            attrs.getPrice(),
                            attrs.getImageUrl(),
                            inv.getQuantity(),
                            inv.getState()
                    );
                })
                .toList();

        Page<ProductInventoryResponse> pageResult = new PageImpl<>(
                responses,
                PageRequest.of(page, size),
                responses.size()
        );

        return ResponseEntity.ok(pageResult);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductInventoryResponse> getProductWithInventory(@PathVariable UUID productId) {

        Inventory inventory = inventoryRepository.findByProductId(productId).orElse(null);
        int quantity = (inventory != null) ? inventory.getQuantity() : 0;
        InventoryState state = (inventory != null) ? inventory.getState() : InventoryState.OUT_OF_STOCK;

        ProductApiResponse apiResponse;
        try {
            apiResponse = productServiceClient.get()
                    .uri("/products/{id}", productId)
                    .header("X-API-KEY", "producto-api-linktic")
                    .retrieve()
                    .body(ProductApiResponse.class);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Falla al conectar con el servicio de productos: " + e.getMessage(), e);
        }

        if (apiResponse == null || apiResponse.getData() == null || apiResponse.getData().getAttributes() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado o respuesta inválida");
        }

        // Convertir JsonNode -> ProductAttributes
        ProductAttributes attrs = new ObjectMapper()
                .convertValue(apiResponse.getData().getAttributes(), ProductAttributes.class);

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

        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> new Inventory(productId, 0, InventoryState.OUT_OF_STOCK));

        int newQuantity = request.getQuantity();
        if (newQuantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad no puede ser negativa");
        }

        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);

        ProductApiResponse apiResponse;
        try {
            apiResponse = productServiceClient.get()
                    .uri("/products/{id}", productId)
                    .header("X-API-KEY", "producto-api-linktic")
                    .retrieve()
                    .body(ProductApiResponse.class);

        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "No se pudo obtener información del producto: " + e.getMessage(), e);
        }

        if (apiResponse == null || apiResponse.getData() == null || apiResponse.getData().getAttributes() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado o respuesta inválida");
        }

        // 🔥 AQUÍ está el cambio:
        ProductAttributes attrs = mapper.convertValue(
                apiResponse.getData().getAttributes(),
                ProductAttributes.class
        );

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