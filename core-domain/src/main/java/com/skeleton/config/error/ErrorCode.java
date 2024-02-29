package com.skeleton.config.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// @JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

	//TODO Error
	INTERNAL_SERVER_ERROR(1000, "일시적인 오류가 발생했습니다."),
	INVALID_PARAMETER(1001, "올바르지 않은 입력값이 있습니다."),
	INVALID_TOKEN(1002, "올바르지 않은 토큰입니다."),
	EXPIRED_TOKEN(1003, "인증 정보가 만료됐습니다."),
	EMPTY_TOKEN(1004, "Empty token"),
	INVALID_TOKEN_UPDATE(1005, "토큰 갱신 불가"),
	INVALID_UPLOAD_EXTENSION(1006, "Extension 오류"),
	INVALID_UPLOAD_MAX_FILE_SIZE(1007, "최대 file size 오류"),
	INVALID_ACCOUNT(1008, "Invalid Account"),
	STOPPED_ACCOUNT(1009, "정지된 계정"),
	WITHDRAW_ACCOUNT(1010, "탈퇴된 계정"),
	WAITING_ACCOUNT(1011, "대기 중인 계정"),
	RESTORE_ACCOUNT(1012, "복구중인 계정"),
	NOT_FOUND_ACCOUNT(1013, "계정 정보를 찾을 수 없습니다."),
	NOT_FOUND(1014, "Not Found"),
	AUTH_FAIL(1015, "Auth Fail"),
	PRECONDITION_FAIL(1016, "PreCondition Fail"),
	INVALID_TYPE(1018, "Invalid Type"),
	INCOMPLETE_PUSH_TARGET(1019, "Target Configuration Incomplete"),
	INVALID_PUSH_TARGET_PARAMETER(1020, "Target Parameter Configuration Invalid"),
	CONFLICT(1021, "Conflict"),
	BAD_REQUEST(1022, "Bad Request"),

	;

	private final Integer code;
	private final String message;
}
