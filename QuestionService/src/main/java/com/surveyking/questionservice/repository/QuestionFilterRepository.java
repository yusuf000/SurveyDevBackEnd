package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.model.entity.QuestionFilter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface QuestionFilterRepository extends JpaRepository<QuestionFilter, Long> {
    @Modifying(clearAutomatically=true, flushAutomatically = true)
    @Transactional
    void deleteByQuestion(Question question);
}
