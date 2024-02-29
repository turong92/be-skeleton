package com.skeleton.config.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skeleton.util.PageSortUtils;

public interface QueryRepository <T extends DataModel<?>> {
	default <Q extends EntityPathBase<T>> Page<T> getPage(JPAQueryFactory queryFactory, Q q, Predicate predicate, Pageable pageable) {
		QueryResults<T> result = queryFactory
			.selectFrom(q)
			.where(predicate)
			.orderBy(PageSortUtils.orderSpecifiers(pageable, q))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetchResults();

		return new PageImpl<>(result.getResults(), pageable, result.getTotal());
	}

	default <Q extends EntityPathBase<T>> List<T> getList(JPAQueryFactory queryFactory, Q q, Predicate predicate) {
		QueryResults<T> result = queryFactory
			.selectFrom(q)
			.where(predicate)
			.fetchResults();

		return result.getResults();
	}

	default <Q extends EntityPathBase<T>> T get(JPAQueryFactory queryFactory, Q q, Predicate predicate) {
		T result = queryFactory
			.selectFrom(q)
			.where(predicate)
			.fetchOne();

		return result;
	}
}
