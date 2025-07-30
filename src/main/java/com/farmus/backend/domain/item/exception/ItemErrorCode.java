package com.farmus.backend.domain.item.exception;

import com.farmus.backend.domain.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ItemErrorCode implements ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리를 찾을 수 없습니다."),
    ITEM_SOLD_OUT(HttpStatus.BAD_REQUEST, "매진되었습니다."),
    SUB_CATEGORY_COUNT_LIMIT(HttpStatus.BAD_REQUEST, "하위 카테고리는 최대 3개까지만 선택 가능합니다.");
//    IMAGE_REQUIRED(HttpStatus.BAD_REQUEST, "이미지는 필수 입력 값입니다."),
//    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
//    NO_AUTHORITY(HttpStatus.FORBIDDEN, "수정 또는 삭제 권한이 없습니다."),
//    ADD_IMAGE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "이미지 추가는 허용되지 않습니다."),
//    IMAGE_MINIMUM_REQUIRED(HttpStatus.BAD_REQUEST, "이미지는 최소 3개 이상이어야 합니다.");

    private final HttpStatus status;
    private final String message;
}
