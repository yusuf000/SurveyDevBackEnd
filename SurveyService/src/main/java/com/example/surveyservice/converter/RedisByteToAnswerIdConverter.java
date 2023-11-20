package com.example.surveyservice.converter;

import com.example.surveyservice.model.AnswerId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@WritingConverter
@RequiredArgsConstructor
public class RedisByteToAnswerIdConverter implements Converter<AnswerId, byte[]> {
    private final Jackson2JsonRedisSerializer<AnswerId> jackson2JsonRedisSerializer =  new Jackson2JsonRedisSerializer<>(AnswerId.class);

    @Override
    public byte[] convert(AnswerId source) {
        return jackson2JsonRedisSerializer.serialize(source);
    }
}
