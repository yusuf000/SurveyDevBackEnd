package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody Project project){
        return ResponseEntity.ok(projectService.save(project));
    }

    @GetMapping("")
    public ResponseEntity<List<Project>> get(){
        return ResponseEntity.ok(projectService.get());
    }

    @GetMapping(value = "", params = "sasCode")
    public ResponseEntity<Optional<Project>> get(@RequestParam("sasCode") String sasCode){
        return ResponseEntity.ok(projectService.get(sasCode));
    }
}
