package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.ChoiceRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/choice")
@RequiredArgsConstructor
public class ChoiceController {
    private final ChoiceService choiceService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody ChoiceRequest request){
        return ResponseEntity.ok(choiceService.add(request));
    }


    @PostMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("choiceId") Long choiceId){
        return ResponseEntity.ok(choiceService.delete(choiceId));
    }

    @GetMapping("")
    public ResponseEntity<List<Choice>> get(@RequestParam("questionId") Long questionId){
        return ResponseEntity.ok(choiceService.get(questionId));
    }
}
