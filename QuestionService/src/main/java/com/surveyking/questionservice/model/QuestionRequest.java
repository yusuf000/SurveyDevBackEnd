package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.Choice;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private Long serial;
    private String languageCode;
    private String questionType;
    private Long phaseId;
    private String description;
    private List<Choice> choices;
}
