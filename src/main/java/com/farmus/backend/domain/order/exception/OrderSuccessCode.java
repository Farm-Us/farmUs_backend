package com.farmus.backend.domain.order.exception;

import com.farmus.backend.domain.global.success.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum OrderSuccessCode implements SuccessCode {
    ORDER_CREATED(HttpStatus.CREATED, "주문이 성공적으로 등록되었습니다.");

    private final HttpStatus status;
    private final String message;
}
