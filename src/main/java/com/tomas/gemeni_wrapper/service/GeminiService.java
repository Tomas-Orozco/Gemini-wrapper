package com.tomas.gemeni_wrapper.service;

import org.springframework.http.HttpHeaders;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class GeminiService {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper; 

    public GeminiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String sendMessage(String userMessage) {
        String finalUrl = geminiApiUrl + "?key=" + geminiApiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

     
        Map<String, Object> part = new HashMap<>();
        part.put("text", userMessage);

        Map<String, Object> contentItem = new HashMap<>();
        contentItem.put("parts", Collections.singletonList(part));

        Map<String, Object> bodyJson = new HashMap<>();
        bodyJson.put("contents", Collections.singletonList(contentItem));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(bodyJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
            finalUrl,
            requestEntity,
            String.class
        );

        if (response.getStatusCode().is2xxSuccessful()) {
           
            try {
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode textNode = root
                    .path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text");
                return textNode.asText();
            } catch (Exception e) {
                throw new RuntimeException("No se pudo parsear la respuesta JSON de Gemini", e);
            }
        } else {
            throw new RuntimeException("Error en API Gemini: " + response.getStatusCodeValue());
        }
    }
}