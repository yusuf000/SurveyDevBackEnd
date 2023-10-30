package com.surveyking.questionservice.client;

import com.surveyking.questionservice.model.Answer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SURVEY-SERVICE")
public interface SurveyServiceClient {
    @GetMapping(value = "/answer", params = "userId")
    ResponseEntity<List<Answer> > getAllForUser(@RequestParam Long userId);
}
