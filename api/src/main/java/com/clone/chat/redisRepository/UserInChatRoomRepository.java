package com.clone.chat.redisRepository;

import com.clone.chat.domain.UserInChatRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInChatRoomRepository extends CrudRepository<UserInChatRoom, Long> {

    List<UserInChatRoom> findAllByUserId(String userId);

}
