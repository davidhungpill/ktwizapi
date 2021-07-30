package com.kt.wiz.event.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kt.wiz.event.bean.Application;
import com.kt.wiz.event.bean.EventAnswerStatistic;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

	Optional<Application> findByUserId(String userId);
	
	@Query(nativeQuery = true, value =
			"select a.choice as choice, count(*) as cnt " + 
			"from application a " +
			"where a.event_id = ?1 " +
			"group by a.choice")
	List<EventAnswerStatistic> findEventServeyCount(Long eventId);

}
