package com.example.surveyservice.controller;

import com.example.surveyservice.model.BarChartResponse;
import com.example.surveyservice.model.entity.Response;
import com.example.surveyservice.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;

    @GetMapping(value = "/response_count")
    @PreAuthorize("hasAuthority(@Privilege.ANSWER_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromProjectSasCode(#sasCode, #userId)")
    public ResponseEntity<BarChartResponse> getLastFiveDaysResponseCount(@RequestParam String sasCode, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(analyticsService.getLastFiveDaysResponseCount(sasCode));
    }

    @GetMapping(value = "/responses")
    @PreAuthorize("hasAuthority(@Privilege.ANSWER_INFO)" + "&& @ownershipCheckService.checkProjectMembershipFromQuestionId(#questionId, #userId)")
    public ResponseEntity<Page<Response>> findAllByQuestionId(@RequestParam Long questionId, @RequestParam Integer pageNo, @RequestHeader(value = "userId") String userId){
        return ResponseEntity.ok(analyticsService.findAllByQuestionId(questionId, pageNo));
    }
}
