package com.skeleton.domain.test.code;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.skeleton.code.dto.CodeDto;
import com.skeleton.util.EnumUtils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TestStatus implements EnumUtils<TestStatus> {
	DEFAULT(0, "default"),
	ACTIVATED(1, "활성"),
	STOPPED(2, "정지"),
	;

	private final int code;
	private final String name;

	public static final Map<Integer, TestStatus> map =
		Collections.unmodifiableMap(EnumUtils.initializeMapping(TestStatus.class));
	public static final List<CodeDto> list = EnumUtils.initializeList(TestStatus.class);

	@JsonCreator
	public static TestStatus fromCode(Integer code) {
		if (code == null) {
			return null;
		}
		return map.getOrDefault(code, DEFAULT);
	}

	@JsonValue
	public int getCode() { return code; }
}
