package com.clone.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.chat.domain.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
