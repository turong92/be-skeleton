package com.skeleton.config.error.exception.unchecked;

import com.skeleton.config.error.ErrorCode;
import com.skeleton.config.error.exception.DefaultRuntimeException;
import org.springframework.http.HttpStatus;

public class IllegalStatusException extends DefaultRuntimeException {

	private static final HttpStatus httpStatus = HttpStatus.PRECONDITION_FAILED;

	public IllegalStatusException(String message, ErrorCode errorCode) {
		super(message, errorCode, httpStatus);
	}

	public <T> IllegalStatusException(String message, T id, ErrorCode errorCode) {
		super(message + " [id : " + id + "]", errorCode, httpStatus);
	}
}
