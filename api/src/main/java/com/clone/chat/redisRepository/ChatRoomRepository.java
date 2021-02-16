package com.clone.chat.redisRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.chat.domain.Room;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<Room, Long> {

}
