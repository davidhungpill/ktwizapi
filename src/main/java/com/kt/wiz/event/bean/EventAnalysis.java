package com.kt.wiz.event.bean;


import java.util.Set;

import javax.persistence.Entity;

import lombok.Getter;

@Getter
public class EventAnalysis extends Event {
	Set<AIAnswer> answers;
	String analysis;
	
	public EventAnalysis(Event event, AIAnalysis analysis) {
		super(event.getId(), event.getName(), event.getDescription(), event.getEventStartDateTime(), event.getEventEndDateTime(), event.getActivateYN(), event.getQuestions());
		this.answers = analysis.getAiAnswers();
		this.analysis = analysis.getAnalysis();
	}
}
