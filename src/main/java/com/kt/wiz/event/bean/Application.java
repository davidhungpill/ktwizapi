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
	
	private String userId;
	
	private String choice;
	
	@Builder
	public Application(
			long id,
			Event event,
			String userId,
			String choice) {
		this.id = id;
		this.event = event;
		this.userId = userId;
		this.choice = choice;
	}
	
	public Application() {
		
	}
}
