package com.example.inventory.dto.jsonapi;

public class JsonApiResponse<T> {

    private Data<T> data;

    public Data<T> getData() {
        return data;
    }

    public void setData(Data<T> data) {
        this.data = data;
    }

    // 🔥 Factory method
    public static <T> JsonApiResponse<T> of(String id, String type, T attributes) {
        JsonApiResponse<T> response = new JsonApiResponse<>();
        Data<T> data = new Data<>();
        data.setId(id);
        data.setType(type);
        data.setAttributes(attributes);
        response.setData(data);
        return response;
    }

    public static class Data<T> {
        private String id;
        private String type;
        private T attributes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public T getAttributes() {
            return attributes;
        }

        public void setAttributes(T attributes) {
            this.attributes = attributes;
        }
    }
}
