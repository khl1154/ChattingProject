package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = ChatApplication.class)
@Transactional
@ActiveProfiles("dev")
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void 테스트() throws Exception{
        //given
        //when

        //then
    }
}