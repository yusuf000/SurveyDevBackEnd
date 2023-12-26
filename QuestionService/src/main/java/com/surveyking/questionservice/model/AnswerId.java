package com.surveyking.questionservice.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerId implements Serializable {
    private String userId;
    private Long questionId;
}
