package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import com.clone.chat.domain.Message;
import com.clone.chat.domain.Room;
import com.clone.chat.domain.User;
import com.clone.chat.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = ChatApplication.class)
@ActiveProfiles("dev")
class RedisServiceTest {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRepository userRepository;

    private Room testRoom;
    private User testUser1;
    private User testUser2;

    @BeforeEach
    public void createTestData() {
        Long roomId = 1111111112L;
        User user1 = User.builder()
                .id("test1")
                .build().map(User.class);
        User user2 = User.builder()
                .id("test2")
                .build().map(User.class);
        userRepository.save(user1);
        userRepository.save(user2);
        Set<String> inUserIds = new HashSet<>();
        inUserIds.add(user1.getId());
        inUserIds.add(user2.getId());
        Room room = Room.builder()
                .id(roomId)
                .name("새채팅방")
                .inUserIds(inUserIds).build();
        redisService.saveRoom(room);
        testRoom = redisService.findRoom(roomId);
        testUser1 = userRepository.getOne("test1");
        testUser2 = userRepository.getOne("test2");
    }

    @Test
    @DisplayName("레디스 방생성&조회 성공")
    public void saveRoom_findRoom_success() throws Exception {
        //given
        Long roomId = 1111111111L;
        User user1 = User.builder()
                .id("userId1")
                .build().map(User.class);
        User user2 = User.builder()
                .id("userId2")
                .build().map(User.class);
        userRepository.save(user1);
        userRepository.save(user2);
        Set<String> inUserIds = new HashSet<>();
        inUserIds.add(user1.getId());
        inUserIds.add(user2.getId());
        Room room = Room.builder()
                .id(roomId)
                .name("새채팅방")
                .inUserIds(inUserIds).build();

        //when
        redisService.saveRoom(room);

        //then
        Room findRoom = redisService.findRoom(roomId);

        Assertions.assertEquals(room.getId(), findRoom.getId());
    }

    @Test
    @DisplayName("유저 방 참여&조회 성공")
    public void joinRoom_success() throws Exception {
        //given
        //when
        redisService.saveUserInRoom(testUser1.getId(), testRoom.getId());

        //then
        List<Room> userInRooms = redisService.getUserInRooms(testUser1.getId());
        boolean result = false;
        for(Room room : userInRooms)
            if (room.getId().equals(testRoom.getId()))
                result = true;
        Assertions.assertTrue(result);
    }


    @Test
    @DisplayName("메시지 전송&조회 성공")
    public void sendMessage_getMessages_success() throws Exception {
        //given
        Message message = Message.builder()
                .id(111111113L)
                .userId(testUser1.getId())
                .contents("테스트메시지")
                .regDt(ZonedDateTime.now()).build();

        //when
        redisService.sendMessage(testRoom.getId(), message);

        //then
        List<Message> messages = redisService.getMessages(testRoom.getId());
        boolean result = false;
        for(Message m : messages)
            if(m.getId().equals(message.getId())) result = true;
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("1대1 대화방 생성&조회 성공")
    public void createOneToOneRoom_getOneToOneRoom_success() throws Exception {
        //given
        //when
        redisService.createOneToOneRoom(testUser1.getId(), testUser2.getId(), testRoom.getId());

        //then
        Long oneToOneRoomId = redisService.getOneToOneRoom(testUser1.getId(), testUser2.getId());
        Assertions.assertEquals(testRoom.getId(), oneToOneRoomId);
    }
}
