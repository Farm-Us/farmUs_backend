package com.farmus.backend.domain.item.exception;

import com.farmus.backend.domain.global.exception.BaseException;
import com.farmus.backend.domain.global.exception.ErrorCode;

public class ItemException extends BaseException {
    public ItemException(ErrorCode errorCode) {
        super(errorCode);
    }
}
