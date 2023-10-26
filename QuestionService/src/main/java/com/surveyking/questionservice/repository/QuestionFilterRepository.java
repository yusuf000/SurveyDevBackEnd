package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.QuestionFilter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionFilterRepository extends JpaRepository<QuestionFilter, Long> {
}
