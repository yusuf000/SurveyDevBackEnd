package com.example.surveyservice.model.entity;

import com.example.surveyservice.model.AnswerId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document("Response")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @Id
    private AnswerId id;

    private String sasCode;

    private Set<Answer> answers;
}
