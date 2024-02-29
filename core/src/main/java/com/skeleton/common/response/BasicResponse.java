package com.skeleton.common.response;

import com.skeleton.common.response.base.SuccessResponse;

import lombok.Getter;

@Getter
public class BasicResponse extends SuccessResponse {
	private final String result;
	public BasicResponse() { this.result = "OK"; }
}
