package com.kt.wiz.event.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.kt.wiz.event.bean.AIAnalysis;
import com.kt.wiz.event.bean.Event;

public interface AIAnalysisRepository extends CrudRepository<AIAnalysis, Long>{

	List<AIAnalysis> findByEventAndUseYN(Event event, String useYn);
}
