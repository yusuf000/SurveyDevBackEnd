package com.example.surveyservice.converter;

import com.example.surveyservice.model.AnswerId;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ReadingConverter
@RequiredArgsConstructor
public class RedisStringToAnswerIdConverter implements Converter<String, AnswerId> {
    private final ObjectMapper objectMapper;

    @Override
    public AnswerId convert(String source) {
        try {
            return objectMapper.readValue(source, AnswerId.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Can not convert to AnswerId");
        }
    }
}
