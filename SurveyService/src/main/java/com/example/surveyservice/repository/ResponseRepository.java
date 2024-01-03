package com.example.surveyservice.repository;

import com.example.surveyservice.model.AnswerId;
import com.example.surveyservice.model.entity.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResponseRepository extends MongoRepository<Response, AnswerId> {
    List<Response> findAllByIdQuestionId(Long questionId);
    List<Response> findAllBySasCodeAndIdUserId(String sasCode, String userId);
    Long countByDateAndSasCode(String date, String sasCode);
}
