package com.clone.chat.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.clone.chat.domain.UserInChatRoomId;
import com.clone.chat.dto.ChatRoomListDto;
import com.clone.chat.dto.ProfileDto;
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

    public ChatRoom findOne(Long id) {return chatRoomRepository.findById(id).get();}

    @Transactional
    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.save(chatRoom);
        List<String> userIds = new LinkedList<>();
        userIds.add(chatRoom.getAdminId());
        invite(userIds,chatRoom.getId());
        return chatRoom;
    }

    public List<ChatRoomListDto> getList(String userId) {
        List<UserInChatRoom> userInChatRooms = userInChatRoomRepository.findAllByUserId(userId);
        List<ChatRoom> chatRooms = new LinkedList<>();
        for (UserInChatRoom userInChatRoom : userInChatRooms) {
            Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findById(userInChatRoom.getChatRoom().getId());
            if(optionalChatRoom.isPresent())
                chatRooms.add(optionalChatRoom.get());
        }

        List<ChatRoomListDto> chatRoomListDtos = new LinkedList<>();
        for (ChatRoom chatRoom : chatRooms) {
            List<ProfileDto> profileDtos = new LinkedList<>();
            for(UserInChatRoom userInChatRoom : chatRoom.getInUsers()) {
                User user = userInChatRoom.getUser();
                String filePath = "";
                if(user.getFile() != null) filePath = user.getFile().getFilePath();
                ProfileDto profileDto = new ProfileDto(user.getId(),filePath,user.getNickName(),user.getStatusMsg());
                profileDtos.add(profileDto);
            }
            ChatRoomListDto chatRoomListDto = ChatRoomListDto.builder()
                    .chatRoomId(chatRoom.getId())
                    .chatRoomName(chatRoom.getChatRoomName())
                    .adminId(chatRoom.getAdminId())
                    .inUserCount(profileDtos.size())
                    .profiles(profileDtos)
                    .build();
            chatRoomListDtos.add(chatRoomListDto);
        }
        return chatRoomListDtos;
    }

    @Transactional
    public void invite(List<String> userIds, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).get();
        for (String userId : userIds) {
            Optional<User> user = userRepository.findById(userId);
            if(user.isPresent()) {
                UserInChatRoom userInChatRoom = new UserInChatRoom(chatRoom, user.get());
                Optional<UserInChatRoom> find = userInChatRoomRepository.findById(new UserInChatRoomId(chatRoom.getId(), user.get().getId()));
                if(!find.isPresent()) {
                    userInChatRoomRepository.save(userInChatRoom);
                    chatRoom.getInUsers().add(userInChatRoom);
                }
            }
        }
    }
}
