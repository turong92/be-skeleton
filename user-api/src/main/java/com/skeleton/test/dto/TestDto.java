package com.skeleton.test.dto;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skeleton.domain.test.code.TestStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDto {
	private Integer id;
	private TestStatus testStatus;
	private String str;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private ZonedDateTime createDate;
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private ZonedDateTime updateDate;
}
