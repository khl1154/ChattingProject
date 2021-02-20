package com.clone.chat.repository;

import com.clone.chat.domain.RoomId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomIdRepository extends JpaRepository<RoomId, Long> {
}
