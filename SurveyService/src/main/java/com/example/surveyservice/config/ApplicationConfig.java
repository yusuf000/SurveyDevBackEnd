package com.example.surveyservice.config;

import com.example.surveyservice.converter.RedisAnswerIdToByteConverter;
import com.example.surveyservice.converter.RedisAnswerIdToStringConverter;
import com.example.surveyservice.converter.RedisByteToAnswerIdConverter;
import com.example.surveyservice.converter.RedisStringToAnswerIdConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.convert.RedisCustomConversions;

import java.util.Arrays;

@Configuration
public class ApplicationConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisCustomConversions redisCustomConversions(RedisStringToAnswerIdConverter redisStringToAnswerIdConverter,
                                                         RedisAnswerIdToStringConverter redisAnswerIdToStringConverter,
                                                         RedisByteToAnswerIdConverter redisByteToAnswerIdConverter,
                                                         RedisAnswerIdToByteConverter redisAnswerIdToByteConverter) {
        return new RedisCustomConversions(Arrays.asList(redisStringToAnswerIdConverter, redisAnswerIdToStringConverter, redisByteToAnswerIdConverter, redisAnswerIdToByteConverter));
    }
}
