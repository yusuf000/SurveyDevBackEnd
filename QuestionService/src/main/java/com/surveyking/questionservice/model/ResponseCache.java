package com.surveyking.questionservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class ResponseCache {

    private AnswerId id;

    private String sasCode;

    private Set<Answer> answers;
}
