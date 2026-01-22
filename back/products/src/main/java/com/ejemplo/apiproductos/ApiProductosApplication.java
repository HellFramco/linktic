package com.ejemplo.apiproductos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
    info = @Info(
        title = "API Productos",
        version = "1.0",
        description = "API REST para gestión de productos con autenticación"
    )
)
@SpringBootApplication
public class ApiProductosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiProductosApplication.class, args);
    }
}