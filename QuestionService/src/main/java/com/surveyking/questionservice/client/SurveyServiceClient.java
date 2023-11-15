package com.surveyking.questionservice.client;

import com.surveyking.questionservice.model.Answer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SURVEY-SERVICE")
public interface SurveyServiceClient {
    @GetMapping(value = "/api/v1/answer")
    ResponseEntity<List<Answer> > getAllForUser(
            @RequestParam(value = "sasCode") String sasCode,
            @RequestHeader(value = "userId") String userId,
            @RequestHeader(value = "authorities") String authority);
}
