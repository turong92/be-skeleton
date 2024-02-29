package com.skeleton.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.skeleton.config.constant.PageConstant;
import com.skeleton.config.domain.DataModel;

public class PageSortUtils {
	private static final Map<Class<?>, Set<String>> fieldNamesMap = new ConcurrentHashMap<>();

	public static OffsetBasedPageRequest pageRequest() {
		long offset = Long.parseLong(PageConstant.DEFAULT_OFFSET);
		int limit = Integer.parseInt(PageConstant.DEFAULT_PAGE_SIZE);

		return new OffsetBasedPageRequest(offset, limit, Sort.unsorted());
	}

	public static <T extends PagingQuery, R extends DataModel<?>> OffsetBasedPageRequest pageRequest(T query,
		Class<R> dataModelClass) {
		long offset = query.getOffset();
		int limit = query.getLimit();
		Sort sort = getSort(query.getSort(), dataModelClass);

		return new OffsetBasedPageRequest(offset, limit, sort);
	}

	public static Sort getSort(List<String> properties, Class<? extends DataModel<?>> dataModelClass) {
		Set<String> fieldNames = updateAndGetDomainModelFieldNameMap(dataModelClass);

		Sort sort = Sort.unsorted();
		if (properties == null)
			return sort;

		for (String property : properties) {
			String[] splits = property.split("\\.");
			if (!fieldNames.contains(splits[0])) {
				continue;
			}
			Sort nextSort = Sort.by(splits[0]);
			if (splits.length >= 2 && splits[1].equals("desc")) {
				nextSort = nextSort.descending();
			}
			sort = sort.and(nextSort);
		}
		return sort;
	}

	//    public static <T> PageImpl<T> pageOf(List<T> pageElements, PageRequest pageRequest, int total) {
	//        return new PageImpl<>(pageElements, pageRequest, total);
	//    }
	//
	//    public static <T> PageImpl<T> manualPageOf(List<T> totalElements, PageRequest pageRequest) {
	//        int offset = (int) pageRequest.getOffset();
	//        int pageSize = pageRequest.getPageSize();
	//        int total = totalElements.size();
	//
	//        List<T> pageElements;
	//        if (offset < total) {
	//            pageElements = totalElements.subList(offset, Math.min(offset + pageSize, total));
	//        } else {
	//            pageElements = Collections.emptyList();
	//        }
	//
	//        return new PageImpl<>(pageElements, pageRequest, totalElements.size());
	//    }

	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T extends DataModel<?>> OrderSpecifier<?>[] orderSpecifiers(Pageable pageable, EntityPathBase<T> q) {
		Class<? extends T> dataModelClass = q.getType();
		Sort sort = pageable.getSort();
		if (sort.isEmpty() || sort == Sort.unsorted()) {
			return new OrderSpecifier[] {};
		}

		Set<String> fieldNames = updateAndGetDomainModelFieldNameMap(dataModelClass);
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
		for (Sort.Order order : sort) {
			Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
			if (!fieldNames.contains(order.getProperty())) {
				continue;
			}
			orderSpecifiers.add(new OrderSpecifier(direction, Expressions.path(Object.class, q, order.getProperty())));
		}
		return orderSpecifiers.toArray(OrderSpecifier[]::new);
	}

	private static Set<String> updateAndGetDomainModelFieldNameMap(Class<? extends DataModel<?>> dataModelClass) {
		return fieldNamesMap.computeIfAbsent(dataModelClass,
			c -> Stream.of(getAllFields(c)).map(Field::getName).collect(Collectors.toSet()));
	}

	/**
	 * 상속관계 포함한 모든 필드 리턴
	 * @param clazz
	 */
	private static Field[] getAllFields(Class<?> clazz) {
		List<Field> result = new ArrayList<>();
		Class<?> current = clazz;
		do {
			result.addAll(Stream.of(current.getDeclaredFields()).collect(Collectors.toList()));
			current = current.getSuperclass();
		} while (current != null);
		return result.toArray(new Field[0]);
	}

	//    public static <T extends DataModel<?>, Q extends EntityPathBase<T>> Predicate[] predicates(Q q) {
	//    	HttpServletRequest httpServletRequest = RequestContextUtils.getHttpServletRequest();
	//    	if (httpServletRequest == null) {
	//			return new Predicate[] {};
	//    	}
	//
	//        Set<String> fieldNames = updateAndGetDomainModelFieldNameMap(q.getType());
	//
	//    	String queryString = URLDecoder.decode(httpServletRequest.getQueryString(), StandardCharsets.UTF_8);
	//    	String[] splitQueries = queryString.split("&");
	//		PredicateBuilder builder = PredicateBuilder.where();
	//
	//    	for (String splitQuery : splitQueries) {
	//    		String[] keyValuePair = splitQuery.split("=");
	//    		String key = keyValuePair[0];
	//    		String value = keyValuePair[1];
	//    		String expression = "eq";
	//
	//    		if (key.contains(".")) {
	//    			String[] keyExpPair = key.split("\\.");
	//    			key = keyExpPair[0];
	//    			expression = keyExpPair[1];
	//    		}
	//    		if (!fieldNames.contains(key)) {
	//                continue;
	//    		}
	//
	//    		Class<?> fieldType;
	//    		Class<?> pathType;
	//    		Field pathField;
	//			try {
	//				fieldType = q.getType().getDeclaredField(key).getType();
	//				pathField = q.getClass().getField(key);
	//			} catch (NoSuchFieldException | SecurityException e) {
	//                log.error("Cannot obtain field", e);
	//				return new Predicate[] {};
	//			}
	//
	//			pathType = pathField.getType();
	//
	//			Method expressionMethod = null;
	//			while (expressionMethod == null && fieldType != null) {
	//				expressionMethod = ReflectionUtils.findMethod(pathType, expression, fieldType);
	//				fieldType = fieldType.getSuperclass();
	//			}
	//
	//    		Class<?> returnType = expressionMethod.getReturnType();
	//    		if (!Predicate.class.isAssignableFrom(returnType)) {
	//                log.error("Invalid returnType method. expressionMethod = {}, returnType = {}", expressionMethod.getName(), returnType.getSimpleName());
	//    			return new Predicate[] {};
	//    		}
	//
	//    		Predicate predicate;
	//			try {
	//				predicate = (Predicate) expressionMethod.invoke(pathField.get(q), value);
	//			} catch (IllegalAccessException | InvocationTargetException e) {
	//                log.error("Unexpected exception occurred when invoking. expressionMethod = {}, value = {}", expressionMethod.getName(), value);
	//    			return new Predicate[] {};
	//			}
	//    		builder.and(predicate);
	//    	}
	//
	//    	return builder.build();
	//    }
	//
	//	private static class PredicateBuilder {
	//        private List<Predicate> predicates = new ArrayList<>();
	//
	//        private static PredicateBuilder where() {
	//            return new PredicateBuilder();
	//        }
	//
	//        private PredicateBuilder and(Predicate predicate) {
	//            this.predicates.add(predicate);
	//            return this;
	//        }
	//
	//        private Predicate[] build() {
	//            return predicates.toArray(new Predicate[predicates.size()]);
	//        }
	//    }
}
