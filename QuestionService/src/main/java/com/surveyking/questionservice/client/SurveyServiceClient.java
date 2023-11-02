package com.surveyking.questionservice.client;

import com.surveyking.questionservice.model.Answer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "SURVEY-SERVICE")
public interface SurveyServiceClient {
    @GetMapping(value = "/api/v1/answer")
    ResponseEntity<List<Answer> > getAllForUser(@RequestHeader(value = "userId") String userId);
}
