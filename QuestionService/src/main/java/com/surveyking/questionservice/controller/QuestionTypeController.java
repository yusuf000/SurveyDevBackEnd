package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.entity.QuestionType;
import com.surveyking.questionservice.service.QuestionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/question-type")
@RequiredArgsConstructor
public class QuestionTypeController {
    private final QuestionTypeService questionTypeService;


    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_TYPE_CREATE)")
    public ResponseEntity<Boolean> add(@RequestBody QuestionType questionType){
        return ResponseEntity.ok(questionTypeService.add(questionType));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_TYPE_INFO)")
    public ResponseEntity<List<QuestionType>> get(){
        return ResponseEntity.ok(questionTypeService.get());
    }
}
