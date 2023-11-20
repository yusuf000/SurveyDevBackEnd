package com.example.surveyservice.repository;

import com.example.surveyservice.model.AnswerId;
import com.example.surveyservice.model.entity.AnswerCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRedisRepository extends CrudRepository<AnswerCache, AnswerId> {
    List<AnswerCache> findAllByIdQuestionId(Long questionId);
    List<AnswerCache> findAllBySasCodeAndIdUserId(String sasCode, String userId);
}
