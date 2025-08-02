package com.farmus.backend.domain.comment.exception;

import com.farmus.backend.domain.global.exception.BaseException;
import com.farmus.backend.domain.global.exception.ErrorCode;

public class CommentException extends BaseException {
  public CommentException(ErrorCode errorCode) {
    super(errorCode);
  }
}
