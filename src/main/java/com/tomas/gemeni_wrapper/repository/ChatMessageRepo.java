package com.tomas.gemeni_wrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tomas.gemeni_wrapper.entity.ChatMessage;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {
}
