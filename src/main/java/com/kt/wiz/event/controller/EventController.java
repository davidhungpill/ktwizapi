package com.kt.wiz.event.controller;

import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kt.wiz.event.bean.Application;
import com.kt.wiz.event.bean.Event;
import com.kt.wiz.event.service.EventService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {


	@Autowired
    RestTemplate restTemplate;
	
	@Autowired
	EventService eventService;
	
	@Value("${target.cache.service.name}")
    private String targetCacheServiceName;

	@ApiOperation(value="Event ID로 Event 조회하는 기능", notes="event ID 필요")
	@GetMapping("/id/{id}")
	public @Valid ResponseEntity<Event> getEventAvailable(@Valid @PathVariable("id") long id) {
		
		log.debug("### start [getEventAvailable] in EventController ...");
		Optional<Event> event = eventService.getEvent(id);

		// DB에서 조회하여 없는 경우
		if ( !event.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(event.get(), HttpStatus.OK);

	}
	
	@ApiOperation(value="현재 활성화된 Event를 조회하는 기능", notes="활성화된 Event가 여러개인 경우 1개만 리턴")
	@GetMapping("/Available")
	public @Valid ResponseEntity<Event> getEventAvailable() {
		
		log.debug("### start [getEventAvailable] in EventController ...");
		Optional<Event> event = eventService.getAvailableEvent();
		 
		// DB에서 조회하여 없는 경우
		if ( !event.isPresent() ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}		
		return new ResponseEntity<>(event.get(), HttpStatus.OK);
	}
	
	@ApiOperation(value="Event를 추가하는 기능", notes="event 내용 입력 필요")
	@PostMapping("/event/add")
	public @Valid ResponseEntity<Map<String,String>> addEvent(@RequestBody Event event){
		
		log.debug("### start [addEvent] in EventController ...");
		return new ResponseEntity<>(eventService.addEvent(event), HttpStatus.OK);
		
	}
	
	@ApiOperation(value="Event에 응모하는 기능", notes="Application 내용 입력 필요")
	@PostMapping("/apply")
	public @Valid ResponseEntity<Map<String,String>> applyToEvent(@RequestBody Application application){
		
		log.debug("### start [applyToEvent] in EventController ...");
		return new ResponseEntity<>(eventService.applyToEvent(application), HttpStatus.OK);
		
	}
}