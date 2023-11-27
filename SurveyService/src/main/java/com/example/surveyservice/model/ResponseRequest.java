package com.example.surveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseRequest {
    private Long questionId;
    private String sasCode;
    private Long choiceId;
    private Long serial;
    private String description;
}
