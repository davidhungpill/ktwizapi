package com.kt.wiz.event.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kt.wiz.event.bean.AIAnalysis;
import com.kt.wiz.event.bean.Application;
import com.kt.wiz.event.bean.Event;
import com.kt.wiz.event.bean.EventAnswerStatistic;
import com.kt.wiz.event.bean.Question;
import com.kt.wiz.event.repository.AIAnalysisRepository;
import com.kt.wiz.event.repository.ApplicationRepository;
import com.kt.wiz.event.repository.EventRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private AIAnalysisRepository aiAnalysisRepository;

	public Optional<Event> getEvent(long id) {
		log.debug("### start [getEvent] in EventService ...");
		
		return eventRepository.findById(id);
		
	}

	public Optional<Event> getAvailableEvent() {
		log.debug("### start [getAvailableEvent] in EventService ...");
		
		List<Event> eventlist = eventRepository.findByActivateYNEquals("Y");
		if(!eventlist.isEmpty() && eventlist.size() > 0) {
			return Optional.ofNullable(eventlist.get(0));
		}
		return Optional.ofNullable(null);
	}

	@Transactional
	public Map<String, String> addEvent(Event event) {
		log.debug("### start [addEvent] in EventService ...");
		String result = "success";
		String message = "이벤트 추가에 성공하였습니다.";
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		Event resultEvent = eventRepository.save(event);
				
		if(resultEvent == null) {
			result = "fail";
			message = "DB insert 실패했습니다";			
		}
		
		resultMap.put("result", result);
		resultMap.put("message", "이벤트 번호="+resultEvent.getId()+" "+message);
		
		return resultMap;
	}
	
	@Transactional
	public Map<String,Object> applyToEvent(Application[] applications) {
		log.debug("### start [applyToEvent] in EventService ...");
		log.debug("### application number is {} for the event {}", applications.length, applications[0].getEvent().getId());
		String result = "success";
		String message = "이벤트 응모에 성공하였습니다.";
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		List<EventAnswerStatistic> statisticList = null;
		
		if(applications == null || applications.length == 0) {
			result = "failure";
			message = "등록할 이벤트가 없습니다.";
		}else {
			// 이벤트 응모여부 조회
			List<Application> appHistList = applicationRepository.findByUserIdAndEvent(applications[0].getUserId(), applications[0].getEvent());
			log.debug("### application history count is {}", appHistList.size());

			// 이미 이벤트에 응모한 경우
			if(appHistList != null && appHistList.size() > 0) {
				result = "failure";
				message = "이미 응모한 이벤트 입니다";
			}else {
				// DB에 이벤트 응모 insert
				ArrayList<Application> resultList = new ArrayList<>();
				for(int i=0;i<applications.length;i++){
					resultList.add(applicationRepository.save(applications[i]));
				}
				log.debug("### apply result count is {}", resultList.size());
				// DB insert에 실패한 경우
				if(resultList.size()!=applications.length) {
					result = "fail";
					message = "DB insert 실패했습니다";
					// 롤백 로직 필요?
				}else {
					//List<EventAnswerStatistic> statisticList = null;
					try {
						statisticList = getEventStatistic(applications[0].getEvent().getId());
					} catch(Exception e) {
						e.printStackTrace();
					}
//					if(statisticList != null && !statisticList.isEmpty()){
//						statisticMap = new HashMap<String, Long>();
//						for (EventAnswerStatistic object : statisticList) {
//							statisticMap.put(object.getChoice(),object.getCnt());
//						}
//					}
				}
			}
		}
		
		resultMap.put("result", result);
		resultMap.put("message", message);
		resultMap.put("statistic", statisticList);
		
		return resultMap;
	}
	
	public List<EventAnswerStatistic> getEventStatistic(Long eventId){
		log.debug("### start [getEventStatistic] in EventService ...");
		return applicationRepository.findEventServeyCount(eventId);
	}

	public AIAnalysis getAIAnalysis(Event event, String useYN) {
		log.debug("### start [getEventStatistic] in EventService ...");
		return aiAnalysisRepository.findByEventAndUseYN(event, useYN).get(0);
	}

	public Map<String,String> addAIAnalysis(AIAnalysis aIAnalytics) {
		log.debug("### start [addAIAnalytics] in EventService ...");
		String result = "success";
		String message = "AI분석 내용 추가에 성공하였습니다.";
		HashMap<String, String> resultMap = new HashMap<String, String>();
		
		AIAnalysis resultAnalytics = aiAnalysisRepository.save(aIAnalytics);
		
		if(resultAnalytics == null) {
			result = "fail";
			message = "DB insert 실패했습니다";			
		}
		
		resultMap.put("result", result);
		resultMap.put("message", "AI분석 ID="+resultAnalytics.getId()+" "+message);
		
		return resultMap;
	}
	
}
