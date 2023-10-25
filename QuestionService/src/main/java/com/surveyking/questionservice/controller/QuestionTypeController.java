package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.entity.QuestionType;
import com.surveyking.questionservice.service.QuestionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question-type")
@RequiredArgsConstructor
public class QuestionTypeController {
    private final QuestionTypeService questionTypeService;


    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody QuestionType questionType){
        return ResponseEntity.ok(questionTypeService.add(questionType));
    }

    @GetMapping("")
    public ResponseEntity<List<QuestionType>> get(){
        return ResponseEntity.ok(questionTypeService.get());
    }
}
