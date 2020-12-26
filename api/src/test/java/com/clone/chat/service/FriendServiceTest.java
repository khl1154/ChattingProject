package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import com.clone.chat.domain.File;
import com.clone.chat.domain.Friend;
import com.clone.chat.domain.User;
import com.clone.chat.dto.FileDto;
import com.clone.chat.dto.FriendDto;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.dto.UserDto;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = ChatApplication.class)
@Transactional
@RequiredArgsConstructor
@ActiveProfiles("dev")
class FriendServiceTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendService friendService;
    @Autowired
    FileRepository fileRepository;

    @Test
    public void 테스트() throws Exception{
        //given
        FriendDto friendDto = new FriendDto();
        friendDto.setUserId("id1");
        friendDto.setFriendId("id2");
        friendService.saveFriend(friendDto);
        //when

        //then
    }

    @Test
    @Rollback(false)
    @Transactional
    public void 친구프로필가져오기() throws Exception{
        //given
        // 친구 정보 등록 후 진구 프로필 가져오기

        File file = File.builder()
                .fileSize(1L)
                .fileName("111")
                .originalFileName("222")
                .filePath("11")
                .id(1L)
                .build();
        fileRepository.save(file);

        User user1 = User.builder()
                .id("id1")
                .password("pw1")
                .phone("111")
                .nickName("name1")
                .statusMsg("msg1")
                .build();
        user1.setFile(file);

        User user2 = User.builder()
                .id("id2")
                .password("pw2")
                .phone("222")
                .nickName("name2")
                .statusMsg("msg2")
                .build();
        user2.setFile(file);

        User user3 = User.builder()
                .id("id3")
                .password("pw3")
                .phone("333")
                .nickName("name3")
                .statusMsg("msg3")
                .build();
        user3.setFile(file);
        System.out.println(" ==========================1 ");
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);


//        System.out.println(" ==========================2 ");
//        FriendDto dto1 = new FriendDto();
//        dto1.setUserId(user1.getId());
//        dto1.setFriend(user2.getId());
//
//        FriendDto dto2 = new FriendDto();
//        dto2.setUserId(user1.getId());
//        dto2.setFriend(user3.getId());
//
//        friendService.saveFriend(dto1);
//        friendService.saveFriend(dto2);
//
//        //when
//        System.out.println(" ==========================3 ");
//        List<ProfileDto> list = friendService.getList("id1");
//        //then
//
//        System.out.println("list = " + list.size());
//        for(ProfileDto dto : list) {
//            System.out.println("dto.getUserId() = " + dto.getUserId());
//            System.out.println("dto.getUserId() = " + dto.getFileName());
//            System.out.println("dto.getUserId() = " + dto.getNickName());
//            System.out.println("dto.getUserId() = " + dto.getStatusMsg());
//        }

//        assertThat(list,contains(
//                hasProperty("userId", is(user2.getId()))
//        ));
    }
}