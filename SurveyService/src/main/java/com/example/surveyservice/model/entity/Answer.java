package com.example.surveyservice.model.entity;

import com.example.surveyservice.model.AnswerId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @EmbeddedId
    private AnswerId id;

    private String sasCode;

    private Long choiceId;

    private String description;
}
