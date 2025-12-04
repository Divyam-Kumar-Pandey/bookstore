package com.bookstore.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    /** Data-only: 200 OK, no message */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, null, data);
    }

    /** Message+data: 200 OK */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data);
    }

    /** Message-only: 200 OK */
    public static ApiResponse<Void> successMessage(String message) {
        return new ApiResponse<>(true, message, null);
    }

    /** Error-only: 4xx/5xx, no data */
    public static ApiResponse<Void> error(String message) {
        return new ApiResponse<>(false, message, null);
    }

    /** Error+data */
    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(false, message, data);
    }
}

