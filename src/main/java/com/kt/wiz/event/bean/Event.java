package com.kt.wiz.event.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table
public class Event implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EVENT_ID")
	private long id;
	
	private String name;
	
	private String description;
	
	private String choices;
	
	private Date eventStartDateTime;
	
	private Date eventEndDateTime;
	
	private String activateYN;
	
	@Builder
	public Event(
			long id,
			String name,
			String description,
			String choices,
			Date eventStartDateTime,
			Date eventEndDateTime,
			String activateYN) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.choices = choices;
		this.eventStartDateTime = eventStartDateTime;
		this.eventEndDateTime = eventEndDateTime;
		this.activateYN = activateYN;
	}
	
	public Event() {
		
	}
}
