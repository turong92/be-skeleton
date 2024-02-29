package com.skeleton.common.response.base;

import java.util.List;

import org.springframework.data.domain.Page;

import com.skeleton.common.response.BasicResponse;
import com.skeleton.common.response.DataResponse;
import com.skeleton.common.response.PageResponse;

public class Response {
	public static <T> DataResponse<T> ok(T model) { return new DataResponse<>(model); }
	public static <T> PageResponse<T> ok(Page<T> page) { return new PageResponse<T>(page); }
	public static <T> PageResponse<T> ok(List<T> list) { return new PageResponse<T>(list); }
	public static BasicResponse ok() { return new BasicResponse(); }
}
