package com.skeleton.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.skeleton.code.dto.CodeDto;

public interface EnumUtils <T> {

	int getCode();
	String getName();

	static <T extends EnumUtils<T>> List<CodeDto> initializeList(Class<T> clazz) {

		List<CodeDto> codeDtos = new ArrayList<>();
		for (T s : clazz.getEnumConstants()) {
			CodeDto codeDto = new CodeDto();

			codeDto.setCode(s.getCode());
			codeDto.setName(s.getName());

			codeDtos.add(codeDto);
		}
		return codeDtos;
	}

	static <T extends EnumUtils<T>> HashMap<Integer, T> initializeMapping(Class<T> clazz) {
		HashMap<Integer, T> map = new HashMap<>();
		for (T s : clazz.getEnumConstants()) {
			map.put(s.getCode(), s);
		}
		return map;
	}
}
