package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.entity.Phase;
import com.surveyking.questionservice.service.PhaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Controller
@RequestMapping("/api/v1/phase")
@RequiredArgsConstructor
public class PhaseController {
    private final PhaseService phaseService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_CREATE)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public Mono<ResponseEntity<Boolean>> add(@RequestParam("sasCode")  String sasCode, @RequestHeader(value = "userId") String userId){
        return Mono.just(ResponseEntity.ok(phaseService.add(sasCode)));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_DELETE)" + "&& @ownershipCheckService.checkProjectMembershipFromPhaseId(#phaseId, #userId)")
    public ResponseEntity<Boolean> delete(@RequestParam("phaseId") Long phaseId, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(phaseService.delete(phaseId));
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority(@Privilege.PROJECT_INFO)" + "&& @ownershipCheckService.checkProjectOwner(#sasCode, #userId)")
    public Mono<ResponseEntity<List<Phase>>> get(@RequestParam("sasCode")  String sasCode, @RequestHeader(value = "userId") String userId){
        return Mono.just(ResponseEntity.ok(phaseService.get(sasCode)));
    }
}
