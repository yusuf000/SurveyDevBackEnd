package com.example.surveyservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Answer", timeToLive = 24 * 60 * 60L)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    @Id
    @JsonIgnore
    private Long id;
    private Long choiceId;
    private Long serial;
    private String description;
}
