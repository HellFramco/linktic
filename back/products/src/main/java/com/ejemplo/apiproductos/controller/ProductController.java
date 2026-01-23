package com.ejemplo.apiproductos.controller;

import com.ejemplo.apiproductos.dto.ProductRequestDto;
import com.ejemplo.apiproductos.dto.ProductResponseDto;
// import com.ejemplo.apiproductos.dto.ProductUpdateDto;
import com.ejemplo.apiproductos.dto.ProductUpdateMultipartDto;
import com.ejemplo.apiproductos.events.ProductCreatedEvent;
import com.ejemplo.apiproductos.service.ProductService;
import com.ejemplo.apiproductos.util.JsonApiResponse;
import com.ejemplo.apiproductos.util.ProductState;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

// import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
// import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.math.BigDecimal;
// import java.io.IOException;
// import java.math.BigDecimal;
// import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.beans.factory.annotation.Value;

// import org.springframework.core.io.Resource;
// import org.springframework.core.io.UrlResource;
// import java.nio.file.Path;
// import java.nio.file.Paths;

import org.springframework.cloud.stream.function.StreamBridge;

import java.util.UUID;

@Tag(name = "Productos", description = "API para gestionar productos con autenticación")
@SecurityRequirement(name = "apiKey")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Value("${file.upload-dir}")
    private String uploadDir;
    private final ProductService productService;
    private final StreamBridge streamBridge;

    public ProductController(ProductService productService, StreamBridge streamBridge) {
        this.productService = productService;
        this.streamBridge = streamBridge;
    }

    @Operation(
        summary = "Crear producto",
        description = "Crea un producto usando multipart/form-data. El campo image es un archivo.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                schema = @Schema(implementation = ProductRequestDto.class)
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto creado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "API Key inválida")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonApiResponse<ProductResponseDto>> create(
            @Valid @ModelAttribute ProductRequestDto dto) {

        ProductResponseDto created = productService.create(dto);

        ProductCreatedEvent event = new ProductCreatedEvent();
        event.setId(created.getId());
        event.setName(created.getName());

        streamBridge.send("productCreated-out", event);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JsonApiResponse.single(
                        "products",
                        created.getId().toString(),
                        created
                ));
    }

    @Operation(
        summary = "Obtener producto por uuid",
        description = "Lista el producto obtenido por el uuid"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "API Key inválida")
    })
    @GetMapping("/{id}")
    public ResponseEntity<JsonApiResponse<ProductResponseDto>> getById(@PathVariable UUID id) {
        ProductResponseDto product = productService.findById(id);
        return ResponseEntity.ok(
                JsonApiResponse.single("products", id.toString(), product)
        );
    }

    @Operation(
        summary = "Obtener todos los producto paginados",
        description = "Lista los productos"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Lista de Productos"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "API Key inválida")
    })
    @GetMapping
    public ResponseEntity<JsonApiResponse<Page<ProductResponseDto>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Pageable pageable = PageRequest.of(
                page, size, Sort.by(Sort.Direction.fromString(direction), sortBy)
        );

        Page<ProductResponseDto> products = productService.findAllPaged(pageable);

        JsonApiResponse<Page<ProductResponseDto>> response = new JsonApiResponse<>();
        response.setData(new JsonApiResponse.DataWrapper<>() {{
            setType("products");
            setAttributes(products);
        }});

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Editar producto por uuid",
            description = "Edita los campos del producto en cuestion"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto editado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "API Key inválida")
    })
    @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JsonApiResponse<ProductResponseDto>> update(
            @PathVariable UUID id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) ProductState state,
            @RequestPart(required = false) MultipartFile image
    ) {

        ProductUpdateMultipartDto multipartDto = new ProductUpdateMultipartDto();
        multipartDto.setName(name);
        multipartDto.setPrice(price);
        multipartDto.setDescription(description);
        multipartDto.setState(state);
        multipartDto.setImage(image);

        ProductResponseDto updated = productService.update(id, multipartDto);

        return ResponseEntity.ok(
                JsonApiResponse.single("products", id.toString(), updated)
        );
    }

    @Operation(
        summary = "Eliminar porducto por uuid",
        description = "Elimina los campos del producto en cuestion"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Producto eliminado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "API Key inválida")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
