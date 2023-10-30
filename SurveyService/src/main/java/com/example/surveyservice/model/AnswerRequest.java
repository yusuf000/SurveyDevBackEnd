package com.example.surveyservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
    private Long userId;
    private Long questionId;
    private Long choiceId;
    private String description;
}
