package com.surveyking.questionservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceFilterRequest {
    Long choiceId;
    String expressionToEvaluate;
    String expressionToShow;
}
