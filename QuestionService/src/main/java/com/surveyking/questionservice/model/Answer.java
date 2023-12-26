package com.surveyking.questionservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    private Long questionId;
    private Long choiceId;
    private String description;
    private Long serial;
}
