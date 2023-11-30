package com.example.surveyservice.model.entity;

import com.example.surveyservice.model.AnswerId;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Set;

@RedisHash(value = "Response", timeToLive = 24 * 60 * 60L)
@Getter
@Setter
@Builder
public class ResponseCache {
    @Id
    private AnswerId id;

    @Indexed
    private String sasCode;

    @Reference
    private Set<Answer> answers;
}
