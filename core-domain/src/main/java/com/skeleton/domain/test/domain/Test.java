package com.skeleton.domain.test.domain;

import com.skeleton.config.domain.DataModel;
import com.skeleton.domain.base.BaseEntity;
import com.skeleton.domain.test.code.TestStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "test", schema = "test")
public class Test extends BaseEntity implements DataModel<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "test_status")
	private TestStatus testStatus;

	@Column(name = "str", length = 255)
	private String str;

	@Override
	public void prePersist() {
		super.prePersist();

		if (this.testStatus == null) {
			this.testStatus = TestStatus.DEFAULT;
		}
	}

	@Override
	public Integer id() {
		return this.id;
	}
}
