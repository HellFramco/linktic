package com.ejemplo.apiproductos.util;

import lombok.Data;

@Data
public class JsonApiSingleData<T> {
    private String type = "products";
    private String id;
    private T attributes;
}