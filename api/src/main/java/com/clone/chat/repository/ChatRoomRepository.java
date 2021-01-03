package com.clone.chat.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.chat.domain.ChatRoom;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{
    @EntityGraph(attributePaths = {"inUsers"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<ChatRoom> findById(Long id);
}
