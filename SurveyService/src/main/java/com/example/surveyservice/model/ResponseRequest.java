package com.example.surveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequest {
    private Long questionId;
    private String sasCode;
    private Long choiceId;
    private Long serial;
    private String description;
}
