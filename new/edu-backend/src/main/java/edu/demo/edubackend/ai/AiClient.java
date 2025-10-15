package edu.demo.edubackend.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.Map;

@Service
public class AiClient {
    @Value("${ai.base-url}") String baseUrl;
    @Value("${ai.api-key}") String apiKey;

    private final WebClient client = WebClient.builder().build();

    public String chat(String model, String prompt, Integer maxTokens) {
        var payload = Map.of(
                "model", model,
                "messages", List.of(Map.of("role","user","content", prompt)),
                "temperature", 0.2,
                "max_tokens", maxTokens != null ? maxTokens : 600
        );
        Map resp = client.post()
                .uri(baseUrl + "/chat/completions")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        var choices = (List<Map<String,Object>>) resp.get("choices");
        if (choices == null || choices.isEmpty()) return "No response from model.";
        var message = (Map<String,Object>) choices.get(0).get("message");
        return (String) message.get("content");
    }
}