package com.kt.wiz.event.bean;

import java.io.Serializable;

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
public class AIAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AIANSWER_ID")
	private long id;
	
	private Long question;
	
	private String answer;
	
	@Builder
	public AIAnswer(
			Long question,
			String answer) {
		this.question = question;
		this.answer = answer;
	}
	
	public AIAnswer() {
		
	}
}
