package com.kt.wiz.event.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table
public class Application implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APPLICATION_ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="EVENT_ID")
	private Event event;
	
	@ManyToOne
	@JoinColumn(name="QUESTION_ID")
	private Question question;
	
	
	private String userId;
	
	private String choice;
	
	@Builder
	public Application(
			Event event,
			Question question,
			String userId,
			String choice) {
		this.event = event;
		this.question = question;
		this.userId = userId;
		this.choice = choice;
	}
	
	public Application() {
		
	}
}
