package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.Choice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRequest {
    private Long questionId;
    private Long serial;
    private String value;
    private List<Choice> choices;
}
