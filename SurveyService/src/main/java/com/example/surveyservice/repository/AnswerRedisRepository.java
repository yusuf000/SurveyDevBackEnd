package com.example.surveyservice.repository;

import com.example.surveyservice.model.entity.Answer;
import org.springframework.data.repository.CrudRepository;

public interface AnswerRedisRepository extends CrudRepository<Answer, Long> {
}
