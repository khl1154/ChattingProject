package com.clone.chat.service;

import com.clone.chat.domain.ChatRoom;
import com.clone.chat.domain.User;
import com.clone.chat.domain.UserInChatRoom;
import com.clone.chat.dto.ChatRoomListDto;
import com.clone.chat.repository.UserInChatRoomRepository;
import com.clone.chat.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatService chatService;
    @Autowired
    UserInChatRoomRepository userInChatRoomRepository;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    public void 테스트유저생성() {
        user1 = User.builder()
                .id("id1")
                .password("pw1")
                .phone("111")
                .nickName("name1")
                .statusMsg("msg1")
                .build().map(User.class);

        user2 = User.builder()
                .id("id2")
                .password("pw2")
                .phone("222")
                .nickName("name2")
                .statusMsg("msg2")
                .build().map(User.class);

        user3 = User.builder()
                .id("id3")
                .password("pw3")
                .phone("333")
                .nickName("name3")
                .statusMsg("msg3")
                .build().map(User.class);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    @Test
    public void 채팅방생성() throws Exception{
        //given
        ChatRoom chatRoom = ChatRoom.builder()
                .adminId(user1.getId())
                .chatRoomName("myRoom")
                .build();
        //when
        Long id = chatService.createChatRoom(chatRoom).getId();
        ChatRoom findChatRoom = chatService.findOne(id);
        //then
        assertThat(chatRoom).isEqualTo(findChatRoom);
    }

    @Test
    public void 채팅방리스트() throws Exception{
        //given
        ChatRoom chatRoom1 = ChatRoom.builder()
                .adminId(user1.getId())
                .chatRoomName("myRoom")
                .build();
        ChatRoom chatRoom2 = ChatRoom.builder()
                .adminId(user1.getId())
                .chatRoomName("myRoom")
                .build();
        ChatRoom chatRoom3 = ChatRoom.builder()
                .adminId(user1.getId())
                .chatRoomName("myRoom")
                .build();
        chatService.createChatRoom(chatRoom1);
        chatService.createChatRoom(chatRoom2);
        chatService.createChatRoom(chatRoom3);

        //when
        List<ChatRoomListDto> chatRooms = chatService.getList(user1.getId());

        //then
        assertThat(chatRooms.size()).isEqualTo(3);
    }

    @Test
    public void 채팅방초대() throws Exception{
        //given
        ChatRoom chatRoom = ChatRoom.builder()
                .adminId(user1.getId())
                .chatRoomName("myRoom")
                .build();
        chatService.createChatRoom(chatRoom);
        List<String> inUserIds = new LinkedList<>();
        inUserIds.add(user2.getId());
        inUserIds.add(user3.getId());
        
        //when
        chatService.invite(inUserIds,chatRoom.getId());

        //then
        Assertions.assertThat(chatRoom.getInUsers().size()).isEqualTo(3);
    }

    @Test
    public void 채팅방중복된초대() throws Exception{
        //given
        ChatRoom chatRoom = ChatRoom.builder()
                .adminId(user1.getId())
                .chatRoomName("myRoom")
                .build();
        chatService.createChatRoom(chatRoom);
        List<String> inUserIds = new LinkedList<>();
        inUserIds.add(user2.getId());
        inUserIds.add(user2.getId());

        List<String> inUserIds2 = new LinkedList<>();
        inUserIds.add(user2.getId());
        inUserIds.add(user2.getId());

        //when
        chatService.invite(inUserIds,chatRoom.getId());
        chatService.invite(inUserIds2,chatRoom.getId());

        //then
        Assertions.assertThat(chatRoom.getInUsers().size()).isEqualTo(2);
    }
}