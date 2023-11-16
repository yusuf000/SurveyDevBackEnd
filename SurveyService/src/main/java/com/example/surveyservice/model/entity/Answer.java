package com.example.surveyservice.model.entity;

import com.example.surveyservice.model.AnswerId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("answer")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @Id
    private AnswerId id;

    private String sasCode;

    private Long choiceId;

    private String description;
}
