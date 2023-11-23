package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.ChoiceRequest;
import com.surveyking.questionservice.model.entity.Choice;
import com.surveyking.questionservice.service.ChoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/api/v1/choice")
@RequiredArgsConstructor
public class ChoiceController {
    private final ChoiceService choiceService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_CREATE)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#request.questionId, #userId)")
    public Mono<ResponseEntity<Boolean>> add(
            @RequestBody ChoiceRequest request,
            @RequestHeader(value = "userId") String userId
    ){
        return Mono.just(ResponseEntity.ok(choiceService.add(request)));
    }

    @PostMapping("/add-all")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_CREATE)" + "&& @ownershipCheckService.checkProjectMembershipFromChoiceRequests(#requests, #userId)")
    public Mono<ResponseEntity<Boolean>> add(
            @RequestBody List<ChoiceRequest> requests,
            @RequestHeader(value = "userId") String userId
    ){
        return Mono.just(ResponseEntity.ok(choiceService.add(requests)));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_DELETE)" + "&& @ownershipCheckService.checkProjectMembershipFromChoiceId(#choiceId, #userId)")
    public Mono<ResponseEntity<Boolean>> delete(
            @RequestParam("choiceId") Long choiceId,
            @RequestHeader(value = "userId") String userId
    ){
        return Mono.just(ResponseEntity.ok(choiceService.delete(choiceId)));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#questionId, #userId)")
    public Mono<ResponseEntity<List<Choice>>> get(
            @RequestParam("questionId") Long questionId,
            @RequestHeader(value = "userId") String userId
    ){
        return Mono.just(ResponseEntity.ok(choiceService.get(questionId)));
    }
}
