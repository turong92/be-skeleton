package com.skeleton.common.response;


import java.util.List;

import org.springframework.data.domain.Page;

import com.skeleton.common.response.base.SuccessResponse;

import lombok.Getter;

@Getter
public class PageResponse<T> extends SuccessResponse {
	private List<T> result;
	private MetaData metadata;

	public PageResponse(Page<T> page) {
		this.result = page.getContent();
		this.metadata = new MetaData(page.getTotalElements(), page.getTotalPages());
	}

	public PageResponse(List<T> list) {
		this.result = list;
		this.metadata = new MetaData(list.size(), 1);
	}

	@Getter
	private static class MetaData {
		private final long totalCount;
		private final int totalPages;

		public MetaData(long totalCount, int totalPages) {
			this.totalCount = totalCount;
			this.totalPages = totalPages;
		}
	}
}
