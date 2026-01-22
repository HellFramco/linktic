package com.ejemplo.apiproductos.util;

import lombok.Data;

import java.util.List;

@Data
public class JsonApiResponse<T> {

    private DataWrapper<T> data;

    public static <T> JsonApiResponse<T> single(String type, String id, T attributes) {
        JsonApiResponse<T> response = new JsonApiResponse<>();
        DataWrapper<T> wrapper = new DataWrapper<>();
        wrapper.setType(type);
        wrapper.setId(id);
        wrapper.setAttributes(attributes);
        response.setData(wrapper);
        return response;
    }

    public static <T> JsonApiResponse<List<T>> collection(String type, List<T> attributesList) {
        JsonApiResponse<List<T>> response = new JsonApiResponse<>();
        DataWrapper<List<T>> wrapper = new DataWrapper<>();
        wrapper.setType(type);
        wrapper.setAttributes(attributesList);
        response.setData(wrapper);
        return response;
    }

    @Data
    public static class DataWrapper<T> {
        private String type;
        private String id;
        private T attributes;
    }
}