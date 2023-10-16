package com.surveyking.questionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private Long serial;
    private String languageCode;
    private String questionType;
    private String projectSasCode;
    private String description;
}
