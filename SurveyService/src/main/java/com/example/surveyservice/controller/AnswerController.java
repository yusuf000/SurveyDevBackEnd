package com.example.surveyservice.controller;

import com.example.surveyservice.model.AnswerRequest;
import com.example.surveyservice.model.entity.AnswerCache;
import com.example.surveyservice.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/submit")
    @PreAuthorize("@ownershipCheckService.checkProjectMembershipFromQuestionId(#request.questionId, #userId)")
    public ResponseEntity<Boolean> submit(@RequestBody AnswerRequest request, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(answerService.submit(request, userId));
    }

    @GetMapping(value = "", params = "questionId")
    @PreAuthorize("hasAuthority(@Privilege.ANSWER_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#questionId, #userId)")
    public ResponseEntity<List<AnswerCache> > getAll(@RequestParam Long questionId, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(answerService.getAll(questionId));
    }

    @GetMapping(value = "")
    @PreAuthorize("hasAuthority(@Privilege.ANSWER_INFO)")
    public ResponseEntity<List<AnswerCache> > getAllForUser(@RequestParam String sasCode, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(answerService.getAllForUser(sasCode,userId));
    }

    @PostMapping(value = "/complete")
    @PreAuthorize("hasAuthority(@Privilege.ANSWER_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromProjectSasCode(#sasCode, #userId)")
    public ResponseEntity<Boolean > completeSurvey(@RequestParam String sasCode, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(answerService.completeSurveyForUser(sasCode,userId));
    }
}
