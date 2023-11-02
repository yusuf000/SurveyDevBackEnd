package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_CREATE)")
    public ResponseEntity<Boolean> add(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(questionService.add(request));
    }

    @PostMapping("/add-all")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_CREATE)")
    public ResponseEntity<Boolean> add(@RequestBody List<QuestionRequest> requests) {
        return ResponseEntity.ok(questionService.add(requests));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_DELETE)")
    public ResponseEntity<Boolean> delete(@RequestParam("questionId") Long questionId) {
        return ResponseEntity.ok(questionService.delete(questionId));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_INFO)")
    public ResponseEntity<List<Question>> get(@RequestParam("sasCode") String sasCode) {
        return ResponseEntity.ok(questionService.get(sasCode));
    }

    @GetMapping(value = "", params = "questionId")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_INFO)")
    public ResponseEntity<Optional<Question>> get(@RequestParam("questionId") Long questionId) {
        return ResponseEntity.ok(questionService.get(questionId));
    }

    @GetMapping(value = "/next")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_INFO)")
    public ResponseEntity<Question> getNext(
            @RequestHeader("userId") String userId,
            @RequestParam("questionId") Long questionId
    ) {
        return ResponseEntity.ok(questionService.getNext(userId, questionId));
    }
}
