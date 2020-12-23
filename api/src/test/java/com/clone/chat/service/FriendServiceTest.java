package com.clone.chat.service;

import com.clone.chat.ChatApplication;
import com.clone.chat.dto.FileDto;
import com.clone.chat.dto.FriendDto;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.dto.UserDto;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    @Rollback(false)
    public void 친구프로필가져오기() throws Exception{
        //given
        // 친구 정보 등록 후 진구 프로필 가져오기
        UserDto user1 = new UserDto();
        user1.setId("id1");
        user1.setPassword("pw1");
        user1.setPhone("111");
        user1.setNickName("name1");
        user1.setFileId(1L);
        user1.setStatusMsg("msg1");

        UserDto user2 = new UserDto();
        user2.setId("id2");
        user2.setPassword("pw2");
        user2.setPhone("222");
        user2.setNickName("name2");
        user2.setFileId(1L);
        user2.setStatusMsg("msg2");

        UserDto user3 = new UserDto();
        user3.setId("id3");
        user3.setPassword("pw3");
        user3.setPhone("333");
        user3.setNickName("name3");
        user3.setFileId(1L);
        user3.setStatusMsg("msg3");
        System.out.println(" ==========================1 ");
        userRepository.save(user1.toEntity());
        userRepository.save(user2.toEntity());
        userRepository.save(user3.toEntity());

        FileDto file = FileDto.builder().fileName("기본")
                .originalFileName("기본")
                .id(1L)
                .filePath("/")
                .fileSize(1L)
                .build();
        fileRepository.save(file.toEntity());

        System.out.println(" ==========================2 ");
        FriendDto dto1 = new FriendDto();
        dto1.setUserId(user1.getId());
        dto1.setFriendId(user2.getId());

        FriendDto dto2 = new FriendDto();
        dto2.setUserId(user1.getId());
        dto2.setFriendId(user3.getId());
        friendService.saveFriend(dto1);
        friendService.saveFriend(dto2);
        //when

        System.out.println("user1.getId() = " + user1.getId());
        List<ProfileDto> list = friendService.getList(user1.getId());
        //then
        System.out.println("list = " + list.size());
        for(ProfileDto dto : list) {
            System.out.println("dto.getUserId() = " + dto.getUserId());
            System.out.println("dto.getUserId() = " + dto.getFileName());
            System.out.println("dto.getUserId() = " + dto.getNickName());
            System.out.println("dto.getUserId() = " + dto.getStatusMsg());
        }

    }
}