package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import com.clone.chat.domain.User;
import com.clone.chat.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = ChatApplication.class)
@ActiveProfiles("dev")
class RoomServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomService roomService;

    @Test
    @DisplayName("유저들 정보 조회 성공")
    public void getInUsers_success() throws Exception {
        //given
        Set<String> userIds = new HashSet<>();
        User user1 = User.builder()
                .id("user1")
                .build().map(User.class);
        userIds.add(user1.getId());
        userRepository.save(user1);
        User user2 = User.builder()
                .id("user2")
                .build().map(User.class);
        userIds.add(user2.getId());
        userRepository.save(user2);
        User user3 = User.builder()
                .id("user3")
                .build().map(User.class);
        userIds.add(user3.getId());
        userRepository.save(user3);

        //when
        Set<User> inUsers = roomService.getInUsers(null, userIds);

        //then
        boolean result = true;
        for(User inUser : inUsers) {
            if(!userIds.contains(inUser.getId())) result = false;
        }
        if(!result) Assert.fail();
    }

}
