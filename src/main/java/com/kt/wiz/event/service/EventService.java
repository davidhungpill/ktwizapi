package com.kt.wiz.event.service;

import java.util.HashMap;
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
		if(!eventlist.isEmpty() && eventlist.size() > 1) {
			return Optional.ofNullable(eventlist.get(0));
		}
		return Optional.ofNullable(eventlist.get(0));
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
	public Map<String,Object> applyToEvent(Application application) {
		log.debug("### start [getAvailableEvent] in EventService ...");
		String result = "success";
		String message = "이벤트 응모에 성공하였습니다.";
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String,Long> statisticMap = null;
		// 이벤트 응모여부 조회
		Optional<Application> appHist = applicationRepository.findByUserIdAndEvent(application.getUserId(), application.getEvent());
		
		// 이미 이벤트에 응모한 경우
		if(appHist.isPresent()) {
			result = "fail";
			message = "이미 응모한 이벤트 입니다";					
		}else {
			// DB에 이벤트 응모 insert 
			Application restApp = applicationRepository.save(application);
			// DB insert에 실패한 경우
			if(restApp == null) {
				result = "fail";
				message = "DB insert 실패했습니다";			
			}else {
				List<EventAnswerStatistic> statisticList = null;
				try {
					statisticList = getEventStatistic(application.getEvent().getId());
				} catch(Exception e) {
					e.printStackTrace();
				}
				if(statisticList != null && !statisticList.isEmpty()){
					statisticMap = new HashMap<String, Long>();
					for (EventAnswerStatistic object : statisticList) {
						statisticMap.put(object.getChoice(),object.getCnt());
					}
				}
			}
		}
		
		resultMap.put("result", result);
		resultMap.put("message", message);
		resultMap.put("statistic", statisticMap);
		
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
