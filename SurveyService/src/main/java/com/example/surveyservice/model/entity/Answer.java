package com.example.surveyservice.model.entity;
import com.example.surveyservice.model.AnswerId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    @EmbeddedId
    @JsonIgnore
    private AnswerId id;

    private Long choiceId;

    private String description;
}
