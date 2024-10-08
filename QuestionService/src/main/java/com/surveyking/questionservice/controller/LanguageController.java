package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.entity.Language;
import com.surveyking.questionservice.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/api/v1/language")
@RequiredArgsConstructor
public class LanguageController {
    private final LanguageService languageService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.LANGUAGE_CREATE)")
    public Mono<ResponseEntity<Boolean>> add(@RequestBody Language request){
        return Mono.just(ResponseEntity.ok(languageService.add(request)));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.LANGUAGE_INFO)")
    public ResponseEntity<List<Language>> get(){
        List<Language> response = languageService.get();
        return ResponseEntity.ok(response);
    }
}
