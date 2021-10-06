package com.kt.wiz.event.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.kt.wiz.event.bean.Application;
import com.kt.wiz.event.bean.Event;
import com.kt.wiz.event.bean.EventAnswerStatistic;
import com.kt.wiz.event.bean.Question;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

	List<Application> findByUserIdAndEvent(String userId, Event event);
	
	@Query(nativeQuery = true, value =
			"select a.question_id as question, a.choice as choice, count(*) as cnt " + 
			"from application a " +
			"where a.event_id = ?1 " +
			"group by a.question_id, a.choice")
	List<EventAnswerStatistic> findEventServeyCount(Long eventId);

}
