package com.skeleton.config.error.exception.unchecked;

import com.skeleton.config.error.ErrorCode;
import com.skeleton.config.error.exception.DefaultRuntimeException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends DefaultRuntimeException {

	private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

	public <T> NotFoundException(String message, T id, ErrorCode errorCode) {
		super(message + " [id : " + id + "]", errorCode, httpStatus);
	}
}
