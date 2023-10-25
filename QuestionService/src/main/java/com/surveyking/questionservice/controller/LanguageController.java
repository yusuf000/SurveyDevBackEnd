package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.entity.Language;
import com.surveyking.questionservice.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/language")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody Language request){
        return ResponseEntity.ok(languageService.add(request));
    }

    @GetMapping("")
    public ResponseEntity<List<Language>> get(){
        return ResponseEntity.ok(languageService.get());
    }
}
