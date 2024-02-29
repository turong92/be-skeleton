package com.skeleton.domain.test.code.converter.parameter;

import org.springframework.core.convert.converter.Converter;

import com.skeleton.config.annotation.RequestParameterConverter;
import com.skeleton.domain.test.code.TestStatus;

@RequestParameterConverter
public class TestStatusConverter implements Converter<String, TestStatus> {
	@Override
	public TestStatus convert(String source) {
		return TestStatus.fromCode(Integer.parseInt(source));
	}
}
