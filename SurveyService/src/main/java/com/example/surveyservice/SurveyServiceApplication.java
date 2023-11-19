package com.example.surveyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableMongoRepositories
@EnableRedisRepositories
public class SurveyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyServiceApplication.class, args);
    }

}
