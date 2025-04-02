package com.tomas.gemeni_wrapper.service;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tomas.gemeni_wrapper.entity.ChatMessage;
import com.tomas.gemeni_wrapper.repository.ChatMessageRepo;

@Service
public class ChatHistoryService {

    private final ChatMessageRepo repo;

    public ChatHistoryService(ChatMessageRepo repo) {
        this.repo = repo;
    }

    public void addMessage(String sender, String content) {
        ChatMessage message = new ChatMessage();
        message.setSender(sender);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        repo.save(message);
    }

    public List<ChatMessage> getAll() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "timestamp"));
    }
}

