package com.clone.chat.redisRepository;

import com.clone.chat.domain.RoomMessage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomMessageRepository extends CrudRepository<RoomMessage, Long> {
    @Query("select a from RoomMessage a where a.roomId = :roomId order by a.message.regDt")
    List<RoomMessage> findByRoomId(String roomId);


//    @Modifying
//    @Query("update RoomMessage a set a.confirm = :confirm where a.userId = :userId and a.message.id = :messageId" +
//            "")
//    int updateChecktByMessageIdAndUserId(@Param("confirm") Boolean confirm, @Param("messageId") Long messageId, @Param("userId") String userId);
}
