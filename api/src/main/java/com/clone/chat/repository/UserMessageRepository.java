package com.clone.chat.repository;

import com.clone.chat.domain.Message;
import com.clone.chat.domain.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {

}
