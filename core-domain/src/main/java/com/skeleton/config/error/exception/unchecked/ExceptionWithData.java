package com.skeleton.config.error.exception.unchecked;

import com.skeleton.config.error.ErrorCode;
import com.skeleton.config.error.exception.DefaultRuntimeException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExceptionWithData extends DefaultRuntimeException {

	Object data;

	public ExceptionWithData(String message, ErrorCode errorCode, HttpStatus httpStatus, Object data) {
		super(message, errorCode, httpStatus);
		this.data = data;
	}
}
