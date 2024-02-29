package com.skeleton.config.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	private Integer code;
	private String message;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Object data;

	// Errors가 없다면 응답이 내려가지 않게 처리
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<ValidationError> errors;

	@Getter
	@RequiredArgsConstructor
	public static class ValidationError {
		// @Valid 로 에러가 들어왔을 때, 어느 필드에서 에러가 발생했는 지에 대한 응답 처리
		private final String field;
		private final String message;

		public static ValidationError of(final FieldError fieldError) {
			return new ValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
	}

	public static ErrorResponse of(final ErrorCode errorCode) {
		return of(errorCode, errorCode.getMessage());
	}

	public static ErrorResponse of(final ErrorCode errorCode, String message) {
		return new ErrorResponse(errorCode.getCode(), message, "", new ArrayList<>());
	}

	public static ErrorResponse of(final ErrorCode errorCode, List<ValidationError> errors) {
		return new ErrorResponse(errorCode.getCode(), errorCode.getMessage(), "", errors);
	}

	public static ErrorResponse withData(final ErrorCode errorCode, String message, Object data) {
		return new ErrorResponse(errorCode.getCode(), message, data, new ArrayList<>());
	}
}

