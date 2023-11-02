package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.QuestionFilterRequest;
import com.surveyking.questionservice.service.QuestionFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/v1/question-filter")
@RequiredArgsConstructor
public class QuestionFilterController {
    private final QuestionFilterService questionFilterService;
    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_CREATE)")
    public ResponseEntity<Boolean> add(@RequestBody QuestionFilterRequest request){
        return ResponseEntity.ok(questionFilterService.add(request));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_DELETE)")
    public ResponseEntity<Boolean> add(@RequestParam Long questionId){
        return ResponseEntity.ok(questionFilterService.delete(questionId));
    }
}
