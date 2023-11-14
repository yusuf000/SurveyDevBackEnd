package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_CREATE)")
    public ResponseEntity<Boolean> add(@RequestBody Project project, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(projectService.add(project,userId));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_DELETE)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public ResponseEntity<Boolean> delete(@RequestParam String sasCode,  @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(projectService.delete(sasCode));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)")
    public ResponseEntity<Optional<List<Project>>> get(@RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(projectService.getAll(userId));
    }

    @GetMapping(value = "", params = "sasCode")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public ResponseEntity<Optional<Project>> get(@RequestParam("sasCode") String sasCode, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(projectService.get(sasCode));
    }

    @PostMapping("/add-member")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_UPDATE)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public ResponseEntity<Boolean> addMember(
            @RequestParam("sasCode") String sasCode,
            @RequestParam("memberId") String memberId,
            @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(projectService.addMember(sasCode, memberId));
    }
}
