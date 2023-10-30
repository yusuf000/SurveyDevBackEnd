package com.example.surveyservice.controller;

import com.example.surveyservice.model.AnswerRequest;
import com.example.surveyservice.model.entity.Answer;
import com.example.surveyservice.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping("/submit")
    public ResponseEntity<Boolean> submit(@RequestBody AnswerRequest request){
        return ResponseEntity.ok(answerService.submit(request));
    }

    @GetMapping("")
    public ResponseEntity<List<Answer> > getAll(@RequestParam Long questionId){
        return ResponseEntity.ok(answerService.getAll(questionId));
    }
}
