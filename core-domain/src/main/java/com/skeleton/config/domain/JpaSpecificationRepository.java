package com.skeleton.config.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaSpecificationRepository <T extends DataModel<ID>, ID> extends JpaRepository<T, ID>, QueryRepository<T> {
}
