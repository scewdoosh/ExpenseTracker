package com.cosa.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class DiscordService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public void sendReport(String webhookUrl, String message) {
        Map<String, String> body = new HashMap<>();
        body.put("content", message);

        webClientBuilder.build()
                .post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe();
    }
}