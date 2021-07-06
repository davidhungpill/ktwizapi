package com.kt.wiz.event.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kt.wiz.event.bean.Event;

public interface EventRepository extends CrudRepository<Event, Long> {

	List<Event> findByActivateYNEquals(String string);

}
