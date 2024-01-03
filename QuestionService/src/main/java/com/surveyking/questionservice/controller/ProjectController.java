package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.ProjectRequest;
import com.surveyking.questionservice.model.RunningProjectResponse;
import com.surveyking.questionservice.model.entity.Project;
import com.surveyking.questionservice.model.entity.ProjectCompletionStatus;
import com.surveyking.questionservice.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_CREATE)")
    public Mono<ResponseEntity<Boolean>> add(@RequestBody ProjectRequest projectRequest, @RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.add(projectRequest, userId)));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_DELETE)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public ResponseEntity<Boolean> delete(@RequestParam String sasCode, @RequestHeader(value = "userId") String userId) {
        return ResponseEntity.ok(projectService.delete(sasCode));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)")
    public Mono<ResponseEntity<Optional<List<Project>>>> get(@RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.getAll(userId)));
    }

    @GetMapping(value = "", params = "sasCode")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public Mono<ResponseEntity<Project>> get(@RequestParam("sasCode") String sasCode, @RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.get(sasCode)));
    }

    @PostMapping("/add-member")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_UPDATE)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public Mono<ResponseEntity<Boolean>> addMember(
            @RequestParam("sasCode") String sasCode,
            @RequestParam("memberId") String memberId,
            @RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.addMember(sasCode, memberId)));
    }

    @PostMapping("/remove-member")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_UPDATE)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public Mono<ResponseEntity<Boolean>> removeMember(
            @RequestParam("sasCode") String sasCode,
            @RequestParam("memberId") String memberId,
            @RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.removeMember(sasCode, memberId)));
    }

    @GetMapping("/member")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public Mono<ResponseEntity<List<String>>> getMembers(
            @RequestParam("sasCode") String sasCode,
            @RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.getMembers(sasCode)));
    }

    @GetMapping("/running")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)")
    public Mono<ResponseEntity<List<RunningProjectResponse>>> getRunningProjectCount(
            @RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.getRunningProject(userId)));
    }

    @PostMapping("/complete")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public Mono<ResponseEntity<Boolean>> completeProjectSurvey(
            @RequestParam("sasCode") String sasCode,
            @RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.completeProjectSurvey(sasCode,userId)));
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)")
    public Mono<ResponseEntity<List<ProjectCompletionStatus>>> isAlreadyCompleted(
            @RequestHeader(value = "userId") String userId) {
        return Mono.just(ResponseEntity.ok(projectService.getProjectCompletionStatuses(userId)));
    }
}
