package com.clone.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.chat.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{

}
