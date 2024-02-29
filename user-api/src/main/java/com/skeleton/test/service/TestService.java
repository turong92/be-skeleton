package com.skeleton.test.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.skeleton.domain.test.domain.QTest;
import com.skeleton.test.mapper.TestMapper;
import com.skeleton.util.PredicateBuilder;
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

	private final JPAQueryFactory jpaQueryFactory;

	private final TestRepository testRepository;

	private final TestMapper testMapper;

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

	public List<TestDto> getTestByStatusList(TestStatus testStatus) {
		PredicateBuilder predicate = new PredicateBuilder()
				.where(testStatus, QTest.test.testStatus::eq);

		return jpaQueryFactory.selectFrom(QTest.test)
				.where(predicate)
				.fetch().stream().map(testMapper::toTestDto)
				.collect(Collectors.toList());
	}

	public TestStatus getTestStatus(TestStatus status) {
		return status;
	}

	@Transactional
	public TestDto createTest(String str) {
		return createTest(testMapper.toTestWithDecorator(str));
	}

	@Transactional
	public TestDto createTest(Test test) {
		return testMapper.toTestDto(testRepository.save(test));
	}

	public TestDto getTest(int id) {
		return testMapper.toTestDto(getTestRaw(id));
	}

	public Test getTestRaw(int id) {
		return testRepository.findById(id).orElseThrow();
	}

	@Transactional
	public TestDto updateTest(TestDto body) {
//		//널체크는 없어서 Null 들어오면 그대로 박히는 방법 mapstruct 등 넣어서 Null check 해줘야함
//		Test testWithoutNullCheck = getTest(body.getId());
//		testWithoutNullCheck.setTestStatus(body.getTestStatus());
//		testWithoutNullCheck.setStr(body.getStr());

		// 아래 처럼 하면 널체크해서 변한 필드만 변경 가능
		Test test = getTestRaw(body.getId());
		testMapper.updateTestFromDto(test, body);

		return testMapper.toTestDto(testRepository.save(test));
	}

	@Transactional
	public void deleteTest(int id) {
		testRepository.deleteById(id);
	}
}
