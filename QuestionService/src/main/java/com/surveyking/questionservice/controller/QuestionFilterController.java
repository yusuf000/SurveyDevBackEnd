package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.QuestionFilterRequest;
import com.surveyking.questionservice.service.QuestionFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/api/v1/question-filter")
@RequiredArgsConstructor
public class QuestionFilterController {
    private final QuestionFilterService questionFilterService;
    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_CREATE)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#request.questionId, #userId)")
    public Mono<ResponseEntity<Boolean>> add(
            @RequestBody QuestionFilterRequest request,
            @RequestHeader("userId") String userId
    ){
        return Mono.just(ResponseEntity.ok(questionFilterService.add(request)));
    }

    @PostMapping("/add-expression")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_CREATE)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#request.questionId, #userId)")
    public Mono<ResponseEntity<Boolean>> addExpression(
            @RequestBody QuestionFilterRequest request,
            @RequestHeader("userId") String userId
    ){
        return Mono.just(ResponseEntity.ok(questionFilterService.add(request.getQuestionId(), request.getExpressionToEvaluate(), request.getExpressionToShow())));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_DELETE)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#questionId, #userId)")
    public ResponseEntity<Boolean> add(
            @RequestParam Long questionId,
            @RequestHeader("userId") String userId
    ){
        return ResponseEntity.ok(questionFilterService.delete(questionId));
    }
}
