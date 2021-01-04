package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import com.clone.chat.domain.User;
import com.clone.chat.dto.FriendDto;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.repository.UserRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest(classes = ChatApplication.class)
@ActiveProfiles("dev")
@Slf4j
@Transactional
class FriendServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendService friendService;
    @Autowired
    FileRepository fileRepository;

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
    public void 친구들프로필가져오기() throws Exception{
        //given
        FriendDto friend1 = new FriendDto();
        friend1.setUserId(user1.getId());
        friend1.setFriendId(user2.getId());

        FriendDto friend2 = new FriendDto();
        friend2.setUserId(user1.getId());
        friend2.setFriendId(user3.getId());

        friendService.saveFriend(friend1);
        friendService.saveFriend(friend2);

        //when
        List<ProfileDto> list = friendService.getList(user1.getId());

        //then
//        assertThat(list,contains(
//                hasProperty("userId", is(user2.getId())),
//                hasProperty("userId", is(user3.getId()))
//                ));
    }
}