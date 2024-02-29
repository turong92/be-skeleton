package com.skeleton.test.mapper;

import com.skeleton.domain.test.domain.Test;
import com.skeleton.test.dto.TestDto;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(TestDecorator.class)
public interface TestMapper {

    TestDto toTestDto(Test test);
    Test toTestWithDecorator(String str);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "testStatus", source = "dto.testStatus")
    @Mapping(target = "str", source = "dto.str")
    // 이름이 같다면 알아서 매핑됨
    Test toTestWithMappingAnnotation(TestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTestFromDto(@MappingTarget Test entity, TestDto dto);
}
