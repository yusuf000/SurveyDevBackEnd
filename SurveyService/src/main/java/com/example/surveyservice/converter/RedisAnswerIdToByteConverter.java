package com.example.surveyservice.converter;

import com.example.surveyservice.model.AnswerId;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
@RequiredArgsConstructor
public class RedisAnswerIdToByteConverter implements Converter<byte[], AnswerId> {
    private final Jackson2JsonRedisSerializer<AnswerId> jackson2JsonRedisSerializer =  new Jackson2JsonRedisSerializer<>(AnswerId.class);

    @Override
    public AnswerId convert(byte[] source) {
        return jackson2JsonRedisSerializer.deserialize(source);
    }
}
