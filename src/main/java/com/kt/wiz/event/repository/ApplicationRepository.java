package com.kt.wiz.event.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.kt.wiz.event.bean.Application;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

	Optional<Application> findByUserId(String userId);

}
