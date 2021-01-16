package com.clone.chat.repository;

import com.clone.chat.domain.Message;
import com.clone.chat.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
