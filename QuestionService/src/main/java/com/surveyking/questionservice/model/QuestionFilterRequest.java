package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.QuestionFilter;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFilterRequest {
    Long questionId;
    QuestionFilter questionFilter;
    String expressionToEvaluate;
    String expressionToShow;
}
