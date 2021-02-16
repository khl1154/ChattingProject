package com.clone.chat.redisRepository;

import com.clone.chat.domain.UserMessage;
import org.springframework.data.repository.CrudRepository;

public interface UserMessageRepository extends CrudRepository<UserMessage, Long> {
}
