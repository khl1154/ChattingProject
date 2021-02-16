package com.clone.chat.redisRepository;

import com.clone.chat.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
