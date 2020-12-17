package com.clone.chat.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clone.chat.domain.ChatRoom;
import com.clone.chat.domain.User;
import com.clone.chat.domain.UserInChatRoom;
import com.clone.chat.model.ChatRoomDto;
import com.clone.chat.model.ChatRoomDto.Response;
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
    public Long chatRoomCreate(ChatRoomDto dto) {
        ChatRoom chatRoom = chatRoomRepository.save(dto.toEntity());

        //연결테이블 처리
        User user = userRepository.getOne(dto.getUserId());
        UserInChatRoom userInChatRoom = new UserInChatRoom(chatRoom, user, true);
        userInChatRoomRepository.save(userInChatRoom);

        return chatRoom.getId();
    }

    public List<Response> getList(String userId, String search) {
        List<UserInChatRoom> list = userInChatRoomRepository.findByUser(userId, search);

        return toDto(list);
    }

    private List<Response> toDto(List<UserInChatRoom> list) {
        List<Response> dtoList = new ArrayList<>();

        for (UserInChatRoom userInChatRoom : list) {
            Response response = new Response();
            response.setChatRoomId(userInChatRoom.getChatRoom().getId());
            response.setChatRoomName(userInChatRoom.getChatRoom().getName());
            response.setLastMsg(null);
            response.setModifiedDate(userInChatRoom.getChatRoom().getModifiedDate());
            response.setUnreadMsgCount(null);

            dtoList.add(response);
        }
        return dtoList;
    }

    @Transactional
    public void invite(List<String> users, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.getOne(chatRoomId);

        for (String userId : users) {
            User user = userRepository.getOne(userId);
            UserInChatRoom userInChatRoom = new UserInChatRoom(chatRoom, user, true);
            userInChatRoomRepository.save(userInChatRoom);
        }
    }
}
