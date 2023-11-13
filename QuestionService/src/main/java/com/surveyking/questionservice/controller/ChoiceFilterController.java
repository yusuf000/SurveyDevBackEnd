package com.surveyking.questionservice.controller;

import com.surveyking.questionservice.model.ChoiceFilterRequest;
import com.surveyking.questionservice.service.ChoiceFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/choice-filter")
@RequiredArgsConstructor
public class ChoiceFilterController {
    private final ChoiceFilterService choiceFilterService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_CREATE)" + "&& @ownershipCheck.checkProjectMembershipFromChoiceId(#request.choiceId, #userId)")
    public ResponseEntity<Boolean> add(
            @RequestBody ChoiceFilterRequest request,
            @RequestHeader(value = "userId") String userId
    ){
        return ResponseEntity.ok(choiceFilterService.add(request));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority(@Privilege.CHOICE_DELETE)" + "&& @ownershipCheck.checkProjectMembershipFromChoiceId(#choiceId, #userId)")
    public ResponseEntity<Boolean> add(
            @RequestParam Long choiceId,
            @RequestHeader(value = "userId") String userId
    ){
        return ResponseEntity.ok(choiceFilterService.delete(choiceId));
    }
}
