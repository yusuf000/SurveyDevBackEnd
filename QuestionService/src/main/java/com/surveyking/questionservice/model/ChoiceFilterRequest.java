package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.ChoiceFilter;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceFilterRequest {
    Long choiceId;
    ChoiceFilter choiceFilter;
}
