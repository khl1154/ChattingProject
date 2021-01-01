package com.clone.chat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clone.chat.domain.ChatRoom;
import com.clone.chat.domain.User;
import com.clone.chat.domain.UserInChatRoom;
import com.clone.chat.model.ChatRoomDto;
import com.clone.chat.repository.ChatRoomRepository;
import com.clone.chat.repository.UserInChatRoomRepository;
import com.clone.chat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserInChatRoomRepository userInChatRoomRepository;

    @Transactional
    public Long createChatRoom(ChatRoomDto dto) {
        Long chatRoomId = chatRoomRepository.save(dto.toEntity()).getId();

//        invite(, chatRoomId);

        return chatRoomId;
    }

//    public List<UserInChatRoom> getList(String userId, String search) {
//        return userInChatRoomRepository.findByUser(userId, search);
//    }

    @Transactional
    public void invite(List<String> users, Long chatRoomId) {

        ChatRoom chatRoom = chatRoomRepository.getOne(chatRoomId);

//        for (String userId : users) {
//            User user = userRepository.getOne(userId);
//            UserInChatRoom userInChatRoom = new UserInChatRoom(chatRoom, user);
//            userInChatRoomRepository.save(userInChatRoom);
//        }
    }
}
