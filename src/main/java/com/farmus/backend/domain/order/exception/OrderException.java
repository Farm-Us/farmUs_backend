package com.farmus.backend.domain.order.exception;

import com.farmus.backend.domain.global.exception.BaseException;
import com.farmus.backend.domain.global.exception.ErrorCode;

public class OrderException extends BaseException {
    public OrderException(ErrorCode errorCode) {
        super(errorCode);
    }
}
