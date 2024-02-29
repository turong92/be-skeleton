package com.skeleton.config.error.exception.unchecked;

import com.skeleton.config.error.ErrorCode;
import com.skeleton.config.error.exception.DefaultRuntimeException;
import org.springframework.http.HttpStatus;

public class UnAuthException extends DefaultRuntimeException {

	private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

	public UnAuthException(String message, ErrorCode errorCode) {
		super(message, errorCode, httpStatus);
	}

	public UnAuthException(boolean messenger) {
		super(ErrorCode.INVALID_ACCOUNT, httpStatus);
	}
}

