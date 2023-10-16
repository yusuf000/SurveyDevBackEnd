package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionTypeRepository extends JpaRepository<QuestionType, Long> {
    Optional<QuestionType> findQuestionTypeByName(String name);
}
