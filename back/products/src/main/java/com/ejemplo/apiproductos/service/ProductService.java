package com.ejemplo.apiproductos.service;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import com.ejemplo.apiproductos.dto.ProductRequestDto;
import com.ejemplo.apiproductos.dto.ProductResponseDto;
import com.ejemplo.apiproductos.entity.Product;
import com.ejemplo.apiproductos.exception.ProductNotFoundException;
import com.ejemplo.apiproductos.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
public class ProductService {

    @Value("${file.upload-dir}")
    private String uploadDir;
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponseDto create(ProductRequestDto dto) {
        String imageUrl = null;

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            imageUrl = saveImage(dto.getImage());
        }

        Product product = mapToEntity(dto);
        product.setImageUrl(imageUrl);

        Product saved = productRepository.save(product);
        return mapToResponseDto(saved);
    }

    public ProductResponseDto findById(UUID id) {
        return productRepository.findById(id)
                .map(this::mapToResponseDto)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Page<ProductResponseDto> findAllPaged(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(this::mapToResponseDto);
    }

    @Transactional
    public ProductResponseDto update(UUID id, ProductRequestDto dto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String imageUrl = saveImage(dto.getImage());
            product.setImageUrl(imageUrl);
        }

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setState(dto.getState());

        Product saved = productRepository.save(product);
        return mapToResponseDto(saved);
    }

    @Transactional
    public void delete(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    private Product mapToEntity(ProductRequestDto dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        p.setDescription(dto.getDescription());
        p.setState(dto.getState());
        return p;
    }

    private ProductResponseDto mapToResponseDto(Product p) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        dto.setDescription(p.getDescription());
        dto.setState(p.getState());
        dto.setImageUrl(p.getImageUrl()); // SOLO EL NOMBRE DEL ARCHIVO
        return dto;
    }

    private String saveImage(MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new RuntimeException("Solo se permiten imágenes");
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            // 👇 usa la ruta del application.yaml
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("SAVE IMAGE: uploadDir=" + uploadDir);
            System.out.println("SAVE IMAGE: fileName=" + fileName);
            System.out.println("SAVE IMAGE: path=" + uploadPath.toAbsolutePath());

            return fileName;

        } catch (IOException e) {
            throw new RuntimeException("Error saving image", e);
        }
    }
}
