package com.farmus.backend.domain.post.exception;

import com.farmus.backend.domain.global.exception.BaseException;
import com.farmus.backend.domain.global.exception.ErrorCode;

public class PostException extends BaseException {
    public PostException(ErrorCode errorCode) {
        super(errorCode);
    }
}
