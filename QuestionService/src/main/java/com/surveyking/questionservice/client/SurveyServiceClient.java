package com.surveyking.questionservice.client;

import com.surveyking.questionservice.model.ResponseCache;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SURVEY-SERVICE")
public interface SurveyServiceClient {
    @GetMapping(value = "/api/v1/answer")
    ResponseEntity<List<ResponseCache>> getAllForUser(
            @RequestParam(value = "phaseId") Long phaseId,
            @RequestHeader(value = "userId") String userId,
            @RequestHeader(value = "authorities") String authority);
}
