package com.skeleton.test.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skeleton.common.response.BasicResponse;
import com.skeleton.common.response.DataResponse;
import com.skeleton.common.response.PageResponse;
import com.skeleton.common.response.base.Response;
import com.skeleton.config.constant.TagConstant;
import com.skeleton.domain.test.code.TestStatus;
import com.skeleton.domain.test.domain.Test;
import com.skeleton.test.dto.TestDto;
import com.skeleton.test.service.TestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = TagConstant.TEST_NAME, description = TagConstant.TEST_DESCRIPTION)
public class TestController {

	private final TestService testService;

	@Operation(summary = "test basic response", description = "test basic response")
	@GetMapping(path = {"/v1.0/test/response/basic"})
	public BasicResponse testBasicResponse() {
		return Response.ok();
	}

	@Operation(summary = "test data response", description = "test data response")
	@GetMapping(path = {"/v1.0/test/response/data"})
	public DataResponse<TestDto> testDataResponse() {
		TestDto testDto = new TestDto();
		testDto.setId(1);
		return Response.ok(testDto);
	}

	@Operation(summary = "test page response", description = "test page response")
	@GetMapping(path = {"/v1.0/test/response/page"})
	public PageResponse<TestDto> testPageResponse() {
		return Response.ok(testService.getTestPage());
	}

	@Operation(summary = "test list response", description = "test list response")
	@GetMapping(path = {"/v1.0/test/response/list"})
	public PageResponse<TestDto> testListResponse() {
		return Response.ok(testService.getTestList());
	}

	@Operation(summary = "test enum-param response", description = "test enum-param response")
	@GetMapping(path = {"/v1.0/test/response/enum-param"})
	public DataResponse<TestStatus> testEnumResponse(
		@Parameter(name = "status", description = "status~") @RequestParam TestStatus status
	) {
		return Response.ok(testService.getTestStatus(status));
	}

	@Tag(name = TagConstant.TEST_CRUD_NAME, description = TagConstant.TEST_CRUD_DESCRIPTION)
	@Operation(summary = "test create", description = "test create")
	@PostMapping(path = {"/v1.0/test/crud/create"})
	public DataResponse<Test> createTest(@Parameter(name = "str", description = "str~") @RequestParam String str) {
		return Response.ok(testService.createTest(str));
	}

	@Tag(name = TagConstant.TEST_CRUD_NAME, description = TagConstant.TEST_CRUD_DESCRIPTION)
	@Operation(summary = "test read", description = "test read")
	@PostMapping(path = {"/v1.0/test/crud/read/{id}"})
	public DataResponse<Test> getTest(@PathVariable(name = "id") int id) {
		return Response.ok(testService.getTest(id));
	}

	@Tag(name = TagConstant.TEST_CRUD_NAME, description = TagConstant.TEST_CRUD_DESCRIPTION)
	@Operation(summary = "test update", description = "test update")
	@PutMapping(path = {"/v1.0/test/crud/update"})
	public DataResponse<Test> updateTest(@RequestBody TestDto body) {
		return Response.ok(testService.updateTest(body));
	}

	@Tag(name = TagConstant.TEST_CRUD_NAME, description = TagConstant.TEST_CRUD_DESCRIPTION)
	@Operation(summary = "test delete", description = "test delete")
	@DeleteMapping(path = {"/v1.0/test/crud/delete/{id}"})
	public BasicResponse deleteTest(@PathVariable(name = "id") int id) {
		testService.deleteTest(id);
		return Response.ok();
	}
}
