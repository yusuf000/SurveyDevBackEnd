package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody QuestionRequest request){
        return ResponseEntity.ok(questionService.save(request));
    }

    @GetMapping("")
    public ResponseEntity<List<Question>> get(@RequestParam("sasCode") String sasCode){
        return ResponseEntity.ok(questionService.get(sasCode));
    }
}
