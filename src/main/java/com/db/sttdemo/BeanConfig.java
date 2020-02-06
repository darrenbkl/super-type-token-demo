package com.db.sttdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                        .baseUrl("http://localhost:8088")
                        .build();
    }
}
