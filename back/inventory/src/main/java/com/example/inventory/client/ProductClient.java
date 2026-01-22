package com.example.inventory.client;

import com.example.inventory.dto.jsonapi.JsonApiResponse;
import com.example.inventory.dto.product.ProductResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient(RestClient productRestClient) {
        this.restClient = productRestClient;
    }

    public ProductResponse getProduct(UUID productId) {

        JsonApiResponse<ProductResponse> response =
                restClient
                        .get()
                        .uri("/products/{id}", productId)
                        .header("X-API-KEY", "producto-api-linktic")
                        .retrieve()
                        .body(new ParameterizedTypeReference<
                                JsonApiResponse<ProductResponse>>() {});

        return response != null
                ? response.getData().getAttributes()
                : null;
    }
}
