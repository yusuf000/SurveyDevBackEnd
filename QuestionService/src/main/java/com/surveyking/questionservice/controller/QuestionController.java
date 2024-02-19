package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.QuestionRequest;
import com.surveyking.questionservice.model.entity.Question;
import com.surveyking.questionservice.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_CREATE)" + "&& @ownershipCheckService.checkProjectMembershipFromPhaseId(#request.phaseId, #userId)")
    public Mono<ResponseEntity<Boolean>> add(
            @RequestBody QuestionRequest request,
            @RequestHeader(value = "userId") String userId
    ) {
        return Mono.just(ResponseEntity.ok(questionService.add(request)));
    }

    @PostMapping("/add-all")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_CREATE)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionRequests(#requests, #userId)")
    public Mono<ResponseEntity<Boolean>> add(
            @RequestBody List<QuestionRequest> requests,
            @RequestHeader(value = "userId") String userId
    ) {
        return Mono.just(ResponseEntity.ok(questionService.add(requests)));
    }

    @PostMapping("/add-csv")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_CREATE)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public Mono<ResponseEntity<Boolean>> addFromCSV(
            @RequestParam MultipartFile file,
            @RequestParam("sasCode") String sasCode,
            @RequestHeader(value = "userId") String userId
    ) {
        return Mono.just(ResponseEntity.ok(questionService.addFromCSV(sasCode, file)));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_DELETE)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#questionId, #userId)")
    public Mono<ResponseEntity<Boolean>> delete(
            @RequestParam("questionId") Long questionId,
            @RequestHeader(value = "userId") String userId
    ) {
        return Mono.just(ResponseEntity.ok(questionService.delete(questionId)));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromPhaseId(#phaseId, #userId)")
    public Mono<ResponseEntity<List<Question>>> getByPhaseId(
            @RequestParam("phaseId") Long phaseId,
            @RequestHeader(value = "userId") String userId
    ) {
        return Mono.just(ResponseEntity.ok(questionService.getByPhaseId(phaseId)));
    }

    @GetMapping("/start")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromPhaseId(#phaseId, #userId)")
    public Mono<ResponseEntity<Question>> startPhase(
            @RequestParam("phaseId") Long phaseId,
            @RequestHeader(value = "userId") String userId
    ) {
        return Mono.just(ResponseEntity.ok(questionService.startPhase(phaseId)));
    }

    @GetMapping(value = "", params = "questionId")
    @PreAuthorize("hasAuthority(@Privilege.QUESTION_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#questionId, #userId)")
    public Mono<ResponseEntity<Question>> getByQuestionId(
            @RequestParam("questionId") Long questionId,
            @RequestHeader(value = "userId") String userId
    ) {
        return Mono.just(ResponseEntity.ok(questionService.get(questionId)));
    }

    @GetMapping(value = "/next")
    @PreAuthorize("hasAuthority(@Privilege.ANSWER_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#questionId, #userId)")
    public Mono<ResponseEntity<Question>> getNext(
            @RequestParam("questionId") Long questionId,
            @RequestHeader("userId") String userId
    ) {
        return Mono.just(ResponseEntity.ok(questionService.getNext(userId, questionId)));
    }
}
