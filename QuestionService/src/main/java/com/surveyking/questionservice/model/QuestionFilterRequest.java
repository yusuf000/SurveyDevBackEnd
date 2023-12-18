package com.surveyking.questionservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFilterRequest {
    Long questionId;
    String expressionToEvaluate;
    String expressionToShow;
}
