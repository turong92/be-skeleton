package com.skeleton.test.mapper;

import com.skeleton.domain.test.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class TestDecorator implements TestMapper {

    @Autowired
    @Qualifier("delegate")
    private TestMapper delegate;

    @Override
    public Test toTestWithDecorator(String str) {
        Test result = delegate.toTestWithDecorator(str);

        //TODO 아래처럼도 가능
//        Test result = new Test();
//        result.setStr(str);
//        result.setTestStatus(TestStatus.ACTIVATED);

        return result;
    }
}
