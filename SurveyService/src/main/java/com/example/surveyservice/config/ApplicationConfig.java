package com.example.surveyservice.config;

import com.example.surveyservice.converter.RedisAnswerIdToStringConverter;
import com.example.surveyservice.converter.RedisStringToAnswerIdConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.util.Arrays;

@Configuration
public class ApplicationConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisCustomConversions redisCustomConversions(RedisStringToAnswerIdConverter redisStringToAnswerIdConverter,
                                                         RedisAnswerIdToStringConverter redisAnswerIdToStringConverter) {
        return new RedisCustomConversions(Arrays.asList(redisStringToAnswerIdConverter, redisAnswerIdToStringConverter));
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        final RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(jackson2JsonRedisSerializer);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setEnableDefaultSerializer(true);

        return template;
    }
}
