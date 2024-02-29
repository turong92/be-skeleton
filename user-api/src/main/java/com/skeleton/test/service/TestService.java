package com.skeleton.test.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skeleton.domain.test.code.TestStatus;
import com.skeleton.domain.test.domain.Test;
import com.skeleton.domain.test.repository.TestRepository;
import com.skeleton.test.dto.TestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	private final TestRepository testRepository;

	public Page<TestDto> getTestPage() {
		Pageable pageable = PageRequest.of(0, 1, Sort.by(Sort.Order.asc("test")));
		return new PageImpl<>(getTestList(), pageable, getTestList().size());
	}

	public List<TestDto> getTestList() {
		List<TestDto> resultList = new ArrayList<>();

		TestDto testDto1 = new TestDto();
		TestDto testDto2 = new TestDto();

		testDto1.setId(1);
		testDto2.setId(2);

		resultList.add(testDto1);
		resultList.add(testDto2);

		return resultList;
	}

	public TestStatus getTestStatus(TestStatus status) {
		return status;
	}

	@Transactional
	public Test createTest(String str) {
		Test test = new Test();
		test.setStr(str);
		return createTest(test);
	}

	@Transactional
	public Test createTest(Test test) {
		return testRepository.save(test);
	}

	public Test getTest(int id) {
		return testRepository.findById(id).orElseThrow();
	}

	@Transactional
	public Test updateTest(TestDto body) {
		//널체크는 없어서 Null 들어오면 그대로 박히는 방법 mapstruct 등 넣어서 Null check 해줘야함
		Test test = getTest(body.getId());
		test.setTestStatus(body.getTestStatus());
		test.setStr(body.getStr());

		return testRepository.save(test);
	}

	@Transactional
	public void deleteTest(int id) {
		testRepository.deleteById(id);
	}
}
