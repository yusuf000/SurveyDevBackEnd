package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.ChoiceFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceFilterRequest {
    Long choiceId;
    ChoiceFilter choiceFilter;
}
