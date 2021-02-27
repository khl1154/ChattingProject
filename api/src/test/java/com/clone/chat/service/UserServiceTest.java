package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.api.anon.model.RequestSignUp;
import com.clone.chat.domain.User;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest(classes = ChatApplication.class)
@ActiveProfiles("dev")
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("회원가입 성공")
    public void signUp_success() throws Exception {
        //given
        RequestSignUp requestSignUp = RequestSignUp.builder()
                .id("signUpId")
                .password("password")
                .nickName("nickName")
                .phone("010-1111-2222")
                .build();

        //when
        userService.signUp(requestSignUp);

        //then
        Optional<User> signUpUser = userRepository.findById("signUpId");
        Assertions.assertTrue(signUpUser.isPresent());
    }

    @Test
    @DisplayName("회원 찾기 성공")
    public void find_success() throws Exception {
        //given
        User user = User.builder()
                .id("userId")
                .build().map(User.class);
        userRepository.save(user);

        //when
        User findUser = userService.find(user.getId());

        //then
        Assertions.assertEquals(user.getId(), findUser.getId());
    }

    @Test
    @DisplayName("회원 찾기 실패")
    public void find_fail() throws Exception {
        try {
            userService.find("notExistId");
        } catch (BusinessException e) {
            org.assertj.core.api.Assertions.assertThat(e.getCode()).isEqualTo(MsgCode.ERROR_ENTITY_NOT_FOUND);
        }
    }
}
