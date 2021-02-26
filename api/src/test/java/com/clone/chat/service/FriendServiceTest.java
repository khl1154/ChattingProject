package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import com.clone.chat.code.MsgCode;
import com.clone.chat.domain.User;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.repository.UserRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import com.clone.chat.ChatApplication;
import com.clone.chat.code.MsgCode;
import com.clone.chat.domain.User;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.FriendService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest(classes = ChatApplication.class)
@ActiveProfiles("dev")
class FriendServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendService friendService;
    @Autowired
    FileRepository fileRepository;

    @Test
    @DisplayName("친구 찾기 성공")
    public void search_success() throws Exception {
        //given
        User user = User.builder()
                .id("findUserId")
                .build().map(User.class);
        userRepository.save(user);

        //when
        User findUser = friendService.search("findUserId");

        //then
        Assert.assertEquals(user.getId(), findUser.getId());
    }

    @Test
    @DisplayName("친구 추가 성공")
    public void addFriend_success() throws Exception {
        //given
        User user = User.builder()
                .id("userId")
                .build().map(User.class);
        User friend = User.builder()
                .id("friendId")
                .build().map(User.class);
        userRepository.save(user);
        userRepository.save(friend);

        //when
        friendService.addFriend(user.getId(), friend.getId());

        //then
        user = userRepository.findById(user.getId()).get();
        boolean result = false;
        for(User inFriend : user.getFriends()) {
            if(inFriend.getId() == friend.getId()) result = true;
        }
        if(!result) Assert.fail();
    }

    @Test
    @DisplayName("친구 추가 실패_존재하지 않는 아이디")
    public void addFriend_fail_entityNotFound() throws Exception {
        //given
        User user = User.builder()
                .id("userId")
                .build().map(User.class);
        userRepository.save(user);

        //when
        //then
        try {
            friendService.addFriend(user.getId(), "notExistUserId");
        } catch (BusinessException e) {
            Assertions.assertThat(e.getCode()).isEqualTo(MsgCode.ERROR_ENTITY_NOT_FOUND);
        }
    }

    @Test
    @DisplayName("친구 추가 실패 _자기 자신을 친구 추가")
    public void addFriend_fail_selfAddFriend() throws Exception {
        //given
        User user = User.builder()
                .id("userId")
                .build().map(User.class);
        userRepository.save(user);

        //when
        //then
        try {
            friendService.addFriend(user.getId(), user.getId());
        } catch (BusinessException e) {
            Assertions.assertThat(e.getCode()).isEqualTo(MsgCode.ERROR_INVALID_FRIEND_RELATIONSHIP);
        }
    }

    @Test
    @DisplayName("친구 추가 실패_이미 친구 관계")
    public void addFriend_fail_alreadyFriend() throws Exception {
        //given
        User user = User.builder()
                .id("userId")
                .build().map(User.class);
        User friend = User.builder()
                .id("friendId")
                .build().map(User.class);
        userRepository.save(user);
        userRepository.save(friend);
        friendService.addFriend(user.getId(), friend.getId());

        //when
        //then
        try {
            friendService.addFriend(user.getId(), friend.getId());
        } catch (BusinessException e) {
            Assertions.assertThat(e.getCode()).isEqualTo(MsgCode.ERROR_INVALID_FRIEND_RELATIONSHIP);
        }
    }
}
