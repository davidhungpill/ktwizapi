package com.kt.wiz.event.bean;


import javax.persistence.Entity;

import lombok.Getter;

@Getter
public class EventAnalysis extends Event {
	String answers;
	String analysis;
	
	public EventAnalysis(Event event, AIAnalysis analysis) {
		super(event.getId(), event.getName(), event.getDescription(), event.getChoices(), event.getEventStartDateTime(), event.getEventEndDateTime(), event.getActivateYN());
		this.answers = analysis.getAnswers();
		this.analysis = analysis.getAnalysis();
	}
}
