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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
@Table
public class AIAnalysis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AIANALYSIS_ID")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="EVENT_ID")
	private Event event;
	
	private String useYN;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "AIANALYSIS_ID")
	private Set<AIAnswer> aiAnswers;
	
	@Column(length = 3000)
	private String analysis;
	
	@Builder
	public AIAnalysis(
			Event event,
			String useYN,
			Set<AIAnswer> aiAnswers,
			String analysis) {
		this.event = event;
		this.useYN = useYN;
		this.aiAnswers = aiAnswers;
		this.analysis = analysis;
	}
	
	public AIAnalysis() {
		
	}
}