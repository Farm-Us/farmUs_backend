package com.farmus.backend.domain.global.success;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class SuccessResponse<T> {
    private final int status;
    private final String message;
    private final T data;

    public static <T> SuccessResponse<T> of(SuccessCode code, T data) {
        return new SuccessResponse<>(code.getStatus().value(), code.getMessage(), data);
    }

    public static <T> SuccessResponse<T> of(SuccessCode code) {
        return new SuccessResponse<>(code.getStatus().value(), code.getMessage(), null);
    }

    public static <T> SuccessResponse<T> of(HttpStatus status, String message, T data) {
        return new SuccessResponse<>(status.value(), message, data);
    }
}
