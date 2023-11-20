package com.example.surveyservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "QUESTION-SERVICE")
public interface OwnershipClient {
    @GetMapping(value = "/api/v1/ownership/by-question-id")
    ResponseEntity<Boolean> checkProjectMembershipFromQuestionId(@RequestParam Long questionId,
                                                                 @RequestHeader(value = "userId") String userId
    );

    @GetMapping(value = "/api/v1/ownership/by-sas-code")
    ResponseEntity<Boolean> checkProjectMembershipFromProjectSasCode(@RequestParam String sasCode,
                                                                     @RequestHeader(value = "userId") String userId
    );
}

