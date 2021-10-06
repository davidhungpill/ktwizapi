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
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "QUESTION_ID")
	private long id;
	
	private String content;
	
	private String choices;
	
	@Builder
	public Question(
			String content,
			String choices) {
		this.content = content;
		this.choices = choices;
	}
	
	public Question() {
		
	}
	
}
