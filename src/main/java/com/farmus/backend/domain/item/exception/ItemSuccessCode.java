package com.farmus.backend.domain.item.exception;

import com.farmus.backend.domain.global.success.SuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemSuccessCode implements SuccessCode {

    ITEM_CREATED(HttpStatus.CREATED, "상품이 성공적으로 등록되었습니다.");

    private final HttpStatus status;
    private final String message;
}
