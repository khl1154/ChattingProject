package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import com.clone.chat.domain.User;
import com.clone.chat.repository.UserRepository;
import org.assertj.core.api.Assertions;
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
    @Autowired
    UserRepository userRepository;

    @Test
    public void 중복확인() throws Exception{
        //given
        User user = User.builder()
                .id("id1")
                .password("pw1")
                .phone("111")
                .nickName("name1")
                .statusMsg("msg1")
                .build().map(User.class);
        userRepository.save(user);

        //when
        boolean isDuplication1 = userService.duplicateId(user.getId());
        boolean isDuplication2 = userService.duplicateId("not exist id");

        //then
        Assertions.assertThat(isDuplication1).isEqualTo(true);
        Assertions.assertThat(isDuplication2).isEqualTo(false);
    }
}