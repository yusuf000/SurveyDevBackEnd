package com.surveyking.questionservice.model;

import com.surveyking.questionservice.model.entity.Choice;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChoiceRequest {
    private Long questionId;
    private Long serial;
    private String value;
    private List<Choice> choices;
}
