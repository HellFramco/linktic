package com.ejemplo.apiproductos.repository;

import com.ejemplo.apiproductos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}