package com.tomas.gemeni_wrapper.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tomas.gemeni_wrapper.entity.ChatMessage;
import com.tomas.gemeni_wrapper.service.ChatHistoryService;
import com.tomas.gemeni_wrapper.service.GeminiService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final GeminiService geminiService;
    private final ChatHistoryService chatHistoryService;

    public ChatController(GeminiService geminiService,
                          ChatHistoryService chatHistoryService) {
        this.geminiService = geminiService;
        this.chatHistoryService = chatHistoryService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody Map<String, String> payload) {
        String userMessage = payload.get("message");

    
        chatHistoryService.addMessage("user", userMessage);

        
        String geminiResponse = geminiService.sendMessage(userMessage);

       
        chatHistoryService.addMessage("gemini", geminiResponse);

       
        Map<String, String> response = new HashMap<>();
        response.put("reply", geminiResponse);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<ChatMessage>> getHistory() {
        List<ChatMessage> history = chatHistoryService.getAll();
        return ResponseEntity.ok(history);
    }
}

