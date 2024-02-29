package com.skeleton.util;

import java.util.List;

import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.skeleton.config.constant.PageConstant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class PagingQuery {
	@Positive
	@Schema(defaultValue = PageConstant.DEFAULT_PAGE_SIZE)
	private int limit = Integer.parseInt(PageConstant.DEFAULT_PAGE_SIZE);

	@PositiveOrZero
	@Schema(defaultValue = PageConstant.DEFAULT_OFFSET,
		description = "이전 조회한 마지막 순서. ex) 10개의 pageSize 에서 1페이지(1~10) 조회 후 다음 패이지 조회 시 10 입력.")
	private int offset = Integer.parseInt(PageConstant.DEFAULT_OFFSET);

	@Schema(description = "id.desc는 default로 박혀있으니 나머지 정렬 원하는 우선순위별로 list 형식으로 나열")
	private List<String> sort;

	@JsonIgnore
	private static final String DEFAULT_SORT_OPTION = "id.desc";

	public List<String> getSort() {
		if (ObjectUtils.isEmpty(sort)) {
			return List.of(DEFAULT_SORT_OPTION);
		}
		return sort;
	}
}
