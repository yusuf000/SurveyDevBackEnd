package com.example.surveyservice.repository;

import com.example.surveyservice.model.AnswerId;
import com.example.surveyservice.model.entity.ResponseCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseRedisRepository extends CrudRepository<ResponseCache, AnswerId> {
    List<ResponseCache> findAllByIdQuestionId(Long questionId);
    List<ResponseCache> findAllBySasCodeAndIdUserId(String sasCode, String userId);
}
