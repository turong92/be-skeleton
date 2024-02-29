package com.skeleton.domain.base;

import java.time.ZonedDateTime;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

	@NotNull
	@Comment("생성일")
	@Column(name = "create_date", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
	private ZonedDateTime createDate;

	@NotNull
	@Comment("수정일")
	@Column(name = "update_date", nullable = false, columnDefinition = "datetime DEFAULT CURRENT_TIMESTAMP")
	private ZonedDateTime updateDate;

	@PrePersist
	public void prePersist() {
		createDate = ZonedDateTime.now();
		updateDate = ZonedDateTime.now();
	}

	@PreUpdate
	void preUpdate() {
		updateDate = ZonedDateTime.now();
	}
}
