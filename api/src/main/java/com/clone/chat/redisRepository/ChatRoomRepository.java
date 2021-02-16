package com.clone.chat.redisRepository;

import com.clone.chat.domain.Room;
import org.springframework.data.repository.CrudRepository;

public interface ChatRoomRepository extends CrudRepository<Room, Long> {

}
