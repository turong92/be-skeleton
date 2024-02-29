package com.skeleton.domain.test.code.converter.db;

import com.skeleton.domain.test.code.TestStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TestStatusConverter implements AttributeConverter<TestStatus, Integer> {
	@Override
	public Integer convertToDatabaseColumn(TestStatus attribute) {
		if (attribute == null) {
			return TestStatus.DEFAULT.getCode();
		}
		return attribute.getCode();
	}

	@Override
	public TestStatus convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
			return TestStatus.DEFAULT;
		}
		return TestStatus.map.getOrDefault(dbData, TestStatus.DEFAULT);
	}
}
