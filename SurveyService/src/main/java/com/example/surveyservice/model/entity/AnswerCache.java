package com.example.surveyservice.model.entity;

import com.example.surveyservice.model.AnswerId;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Answer")
@Data
@Builder
public class AnswerCache {
    @Id
    private AnswerId id;

    private String sasCode;

    private Long choiceId;

    private String description;
}
