package com.example.surveyservice.model;

import lombok.*;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerId implements Serializable {
    @Indexed
    private String userId;
    @Indexed
    private Long questionId;
}
