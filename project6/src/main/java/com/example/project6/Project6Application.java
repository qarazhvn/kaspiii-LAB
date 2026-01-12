package com.example.project6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class Project6Application {

    public static void main(String[] args) {
        SpringApplication.run(Project6Application.class, args);
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

}

