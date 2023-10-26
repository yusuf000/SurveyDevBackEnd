package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.ChoiceFilterRequest;
import com.surveyking.questionservice.service.ChoiceFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/choice-filter")
@RequiredArgsConstructor
public class ChoiceFilterController {
    private final ChoiceFilterService choiceFilterService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody ChoiceFilterRequest request){
        return ResponseEntity.ok(choiceFilterService.add(request));
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> add(@RequestParam Long choiceId){
        return ResponseEntity.ok(choiceFilterService.delete(choiceId));
    }
}
