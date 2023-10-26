package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.QuestionFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionFilterRequest {
    Long questionId;
    QuestionFilter questionFilter;
}
