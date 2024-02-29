package com.skeleton.config.error.exception;

import com.skeleton.config.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DefaultRuntimeException extends RuntimeException {

	private final ErrorCode errorCode;
	private final HttpStatus httpStatus;

	public DefaultRuntimeException(String toStackTrace, ErrorCode errorCode, HttpStatus httpStatus) {
		super(toStackTrace);
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	public DefaultRuntimeException(String toStackTrace, ErrorCode errorCode) {
		super(toStackTrace);
		this.errorCode = errorCode;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}

	public DefaultRuntimeException(ErrorCode errorCode, HttpStatus httpStatus) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.httpStatus = httpStatus;
	}

	public DefaultRuntimeException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
