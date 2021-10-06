package com.kt.wiz.event.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	
	private Date eventStartDateTime;
	
	private Date eventEndDateTime;
	
	private String activateYN;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "EVENT_ID")
	private Set<Question> questions;
	
	@Builder
	public Event(
			String name,
			String description,
			Date eventStartDateTime,
			Date eventEndDateTime,
			String activateYN,
			Set<Question> questions) {
		this.name = name;
		this.description = description;
		this.eventStartDateTime = eventStartDateTime;
		this.eventEndDateTime = eventEndDateTime;
		this.activateYN = activateYN;
		this.questions = questions;
	}
	
	public Event(
			Long id,
			String name,
			String description,
			Date eventStartDateTime,
			Date eventEndDateTime,
			String activateYN,
			Set<Question> questions) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.eventStartDateTime = eventStartDateTime;
		this.eventEndDateTime = eventEndDateTime;
		this.activateYN = activateYN;
		this.questions = questions;
	}
	
	public Event() {
		
	}

}
