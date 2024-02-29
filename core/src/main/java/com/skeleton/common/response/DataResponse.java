package com.skeleton.common.response;

import com.skeleton.common.response.base.SuccessResponse;

import lombok.Getter;

@Getter
public class DataResponse<T> extends SuccessResponse {
	private final T result;
	public DataResponse(T result) {
		this.result = result;
	}
}
