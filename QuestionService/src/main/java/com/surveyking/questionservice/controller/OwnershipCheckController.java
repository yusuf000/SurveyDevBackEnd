package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.service.OwnershipCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/ownership")
@RequiredArgsConstructor
public class OwnershipCheckController {
    private final OwnershipCheckService ownershipCheckService;

    @GetMapping("by-question-id")
    public ResponseEntity<Boolean> checkProjectMembershipFromQuestionId(@RequestParam Long questionId, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(ownershipCheckService.checkProjectMembershipFromQuestionId(questionId, userId));
    }
}
