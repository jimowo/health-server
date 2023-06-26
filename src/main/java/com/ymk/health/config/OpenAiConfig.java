package com.ymk.health.config;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * OpenAi配置类
 */
@Configuration
public class OpenAiConfig {

    @Value("${openai.key}")
    private String apiKey;
    @Value("${openai.model}")
    private String model;
    @Value("${openai.timeout}")
    private long timeout;

    @Bean
    public OpenAiService openAiService(){
        return new OpenAiService(apiKey, Duration.ofSeconds(timeout));
    }
}
