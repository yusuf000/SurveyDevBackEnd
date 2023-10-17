package com.surveyking.questionservice.repository;

import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.model.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findAllByQuestion(Question question);
}
