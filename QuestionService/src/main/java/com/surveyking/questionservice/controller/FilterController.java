package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.FilterRequest;
import com.surveyking.questionservice.service.FilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filter")
@RequiredArgsConstructor
public class FilterController {
    private final FilterService filterService;
    @PostMapping("/add")
    public ResponseEntity<Boolean> add(@RequestBody FilterRequest request){
        return ResponseEntity.ok(filterService.add(request));
    }
}
