package com.skeleton.util;

import java.io.Serializable;
import java.util.Optional;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class OffsetBasedPageRequest implements Pageable, Serializable {

	private static final long serialVersionUID = -25822477129613575L;
	private final Sort sort;
	private int limit;
	private long offset;

	/**
	 * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
	 *
	 * @param offset zero-based offset.
	 * @param limit  the size of the elements to be returned.
	 * @param sort   can be {@literal null}.
	 */
	public OffsetBasedPageRequest(long offset, int limit, Sort sort) {
		if (offset < 0) {
			throw new IllegalArgumentException("Offset index must not be less than zero!");
		}

		if (limit < 1) {
			throw new IllegalArgumentException("Limit must not be less than one!");
		}
		this.limit = limit;
		this.offset = offset;
		this.sort = sort;
	}

	//    /**
	//     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
	//     *
	//     * @param offset     zero-based offset.
	//     * @param limit      the size of the elements to be returned.
	//     * @param direction  the direction of the {@link Sort} to be specified, can be {@literal null}.
	//     * @param properties the properties to sort by, must not be {@literal null} or empty.
	//     */
	//    public OffsetBasedPageRequest(int offset, int limit, Sort.Direction direction, String... properties) {
	//        this(offset, limit, new Sort(direction, properties));
	//    }

	/**
	 * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
	 *
	 * @param offset zero-based offset.
	 * @param limit  the size of the elements to be returned.
	 */
	public OffsetBasedPageRequest(int offset, int limit) {
		this(offset, limit, Sort.unsorted());
	}

	public static OffsetBasedPageRequest of(int offset, int limit) {
		return new OffsetBasedPageRequest(offset, limit);
	}

	public static OffsetBasedPageRequest of(int offset, int limit, Sort sort) {
		return new OffsetBasedPageRequest(offset, limit, sort);
	}

	@Override
	public boolean isPaged() {
		return Pageable.super.isPaged();
	}

	@Override
	public boolean isUnpaged() {
		return Pageable.super.isUnpaged();
	}

	@Override
	public int getPageNumber() {
		return (int)(offset / limit);
	}

	@Override
	public int getPageSize() {
		return limit;
	}

	public void setPageSize(int limit) {
		this.limit = limit;
	}

	@Override
	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	@Override
	public Sort getSort() {
		return sort;
	}

	@Override
	public Sort getSortOr(Sort sort) {
		return Pageable.super.getSortOr(sort);
	}

	@Override
	public Pageable next() {
		return new OffsetBasedPageRequest(getOffset() + getPageSize(), getPageSize(), getSort());
	}

	public OffsetBasedPageRequest previous() {
		return hasPrevious() ? new OffsetBasedPageRequest(getOffset() - getPageSize(), getPageSize(), getSort()) : this;
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : first();
	}

	@Override
	public Pageable first() {
		return new OffsetBasedPageRequest(0, getPageSize(), getSort());
	}

	@Override
	public Pageable withPage(int pageNumber) {
		return null;
	}

	@Override
	public boolean hasPrevious() {
		return offset > limit;
	}

	@Override
	public Optional<Pageable> toOptional() {
		return Pageable.super.toOptional();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof OffsetBasedPageRequest))
			return false;

		OffsetBasedPageRequest that = (OffsetBasedPageRequest)o;

		return new EqualsBuilder()
			.append(limit, that.limit)
			.append(offset, that.offset)
			.append(sort, that.sort)
			.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
			.append(limit)
			.append(offset)
			.append(sort)
			.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("limit", limit)
			.append("offset", offset)
			.append("sort", sort)
			.toString();
	}
}
