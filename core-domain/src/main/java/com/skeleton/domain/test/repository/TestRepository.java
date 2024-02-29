package com.skeleton.domain.test.repository;

import com.skeleton.config.domain.JpaSpecificationRepository;
import com.skeleton.domain.test.domain.Test;

public interface TestRepository extends JpaSpecificationRepository<Test, Integer> {
}
