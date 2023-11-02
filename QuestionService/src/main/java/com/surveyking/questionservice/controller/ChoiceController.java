package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.ChoiceRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/choice")
@RequiredArgsConstructor
public class ChoiceController {
    private final ChoiceService choiceService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_CREATE)")
    public ResponseEntity<Boolean> add(@RequestBody ChoiceRequest request){
        return ResponseEntity.ok(choiceService.add(request));
    }

    @PostMapping("/add-all")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_CREATE)")
    public ResponseEntity<Boolean> add(@RequestBody List<ChoiceRequest> requests){
        return ResponseEntity.ok(choiceService.add(requests));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_DELETE)")
    public ResponseEntity<Boolean> delete(@RequestParam("choiceId") Long choiceId){
        return ResponseEntity.ok(choiceService.delete(choiceId));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_INFO)")
    public ResponseEntity<List<Choice>> get(@RequestParam("questionId") Long questionId){
        return ResponseEntity.ok(choiceService.get(questionId));
    }
}
