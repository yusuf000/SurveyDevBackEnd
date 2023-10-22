package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.Choice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<Choice> choices;
}
