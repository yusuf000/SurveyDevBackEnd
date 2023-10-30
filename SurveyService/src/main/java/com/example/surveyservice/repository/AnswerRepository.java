package com.example.surveyservice.repository;

import com.example.surveyservice.model.AnswerId;
import com.example.surveyservice.model.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, AnswerId> {
    List<Answer> findAllByIdQuestionId(Long questionId);
    List<Answer> findAllByIdUserId(Long userId);
}
