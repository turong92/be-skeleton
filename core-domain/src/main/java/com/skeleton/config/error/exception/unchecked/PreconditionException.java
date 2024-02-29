package com.skeleton.config.error.exception.unchecked;

import com.skeleton.config.error.ErrorCode;
import com.skeleton.config.error.exception.DefaultRuntimeException;
import org.springframework.http.HttpStatus;

public class PreconditionException extends DefaultRuntimeException {

	private static final HttpStatus httpStatus = HttpStatus.PRECONDITION_FAILED;

	public PreconditionException(String message, ErrorCode errorCode) {
		super(message, errorCode, httpStatus);
	}
}
