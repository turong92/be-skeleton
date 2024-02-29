package com.skeleton.config.error.exception.unchecked;

import com.skeleton.config.error.ErrorCode;
import com.skeleton.config.error.exception.DefaultRuntimeException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends DefaultRuntimeException {

	private static final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

	public ForbiddenException(String message, ErrorCode errorCode) {
		super(message, errorCode, httpStatus);
	}

}

