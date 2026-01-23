package com.example.inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient productServiceClient() {
        return RestClient.builder()
            .baseUrl("http://api-productos:8081")   // Docker
            .defaultHeader("X-API-KEY", "producto-api-linktic")
            .defaultHeader("Accept", "application/json")
            .build();
    }
}
