package com.surveyking.questionservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private AnswerId answerId;
    private Long choiceId;
    private String description;
}
