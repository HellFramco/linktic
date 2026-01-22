package com.ejemplo.apiproductos.util;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {
    
    private List<ErrorDetail> errors;

    public ErrorResponse(String status, String title, String detail) {
        ErrorDetail errorDetail = new ErrorDetail(status, title, detail);
        this.errors = List.of(errorDetail);
    }

    @Data
    public static class ErrorDetail {
        private String status;
        private String title;
        private String detail;

        public ErrorDetail(String status, String title, String detail) {
            this.status = status;
            this.title = title;
            this.detail = detail;
        }
    }
}