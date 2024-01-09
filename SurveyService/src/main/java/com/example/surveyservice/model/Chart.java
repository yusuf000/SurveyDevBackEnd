package com.example.surveyservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chart {
    Long choiceId;
    Integer count;
}
