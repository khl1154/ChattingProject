package com.clone.chat.redisRepository;

import com.clone.chat.domain.Message;
import com.clone.chat.domain.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserMessageRepository extends CrudRepository<UserMessage, Long> {

    @Query(
            "select b from UserMessage a join a.message b where (a.userId = :userId and b.userId = :fromUserId) or (a.userId = :fromUserId and b.userId = :userId)"
    )
    List<Message> findByUserIdAndMessageUserId(String userId, String fromUserId);

}
