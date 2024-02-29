package com.skeleton.config.error.exception.unchecked;

import com.skeleton.config.error.ErrorCode;
import com.skeleton.config.error.exception.DefaultRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidException extends DefaultRuntimeException {

	private static final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	public InvalidException(String message, ErrorCode errorCode) {
		super(message, errorCode, httpStatus);
	}

	public <T> InvalidException(String message, T id, ErrorCode errorCode) {
		super(message + " [id : " + id + "]", errorCode, httpStatus);
	}
}
