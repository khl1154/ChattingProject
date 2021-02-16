package com.clone.chat.service;

import com.clone.chat.domain.Room;
import com.clone.chat.domain.RoomMessage;
import com.clone.chat.domain.UserMessage;
import com.clone.chat.redisRepository.RoomMessageRepository;
import com.clone.chat.redisRepository.RoomRepository;
import com.clone.chat.redisRepository.UserMessageRepository;
import com.clone.chat.redisRepository.UserRoomRepository;
import com.clone.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class UserMessageService {

    @Autowired
    UserMessageRepository userMessageRepository;


    public List<UserMessage> getUserMessages(String userId, String friendId) {
        Iterable<UserMessage> userMessagesAll = userMessageRepository.findAll();
        List<UserMessage> userMessages = new ArrayList<>();
        for (UserMessage userMessage : userMessagesAll) {
            if((userMessage.getUserId().equals(userId) && userMessage.getMessage().getUserId().equals(friendId))
        || (userMessage.getUserId().equals(friendId) && userMessage.getMessage().getUserId().equals(userId)))
                userMessages.add(userMessage);
        }
        return userMessages;
    }
}
