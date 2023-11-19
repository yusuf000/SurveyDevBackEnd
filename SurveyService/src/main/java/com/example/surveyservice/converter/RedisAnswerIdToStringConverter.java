package com.example.surveyservice.converter;

import com.example.surveyservice.model.AnswerId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
@RequiredArgsConstructor
public class RedisAnswerIdToStringConverter implements Converter<AnswerId, String> {
    private final ObjectMapper objectMapper;

    @Override
    public String convert(AnswerId source) {
        try {
            return objectMapper.writeValueAsString(source);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Can not convert AnswerId to String");
        }
    }
}
