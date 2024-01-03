package com.surveyking.questionservice.model;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RunningProjectResponse {
    private String sasCode;
    private String projectName;
    private String startedFor;
}
