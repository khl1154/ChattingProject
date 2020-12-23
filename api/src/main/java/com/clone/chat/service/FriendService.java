package com.clone.chat.service;

import com.clone.chat.domain.Friend;
import com.clone.chat.domain.User;
import com.clone.chat.dto.FriendDto;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.repository.FriendRepository;
import com.clone.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FriendService {

    final FriendRepository friendRepository;
    final UserRepository userRepository;
    final FileRepository fileRepository;

    public void saveFriend(FriendDto friendDto) {
        friendRepository.save(friendDto.toEntity());
    }

    public List<ProfileDto> getList(String userId) {
        // 유저 아이디에 해당하는 친구 아이디를 다 가져온다.
        List<Friend> friends = friendRepository.findAllByFriendInfoIdUserId(userId);
        // 친구 아이디에 해당하는 유저 정보를 다 가져온다.
        List<ProfileDto> profileDtos = new LinkedList<>();
        for(Friend friend : friends) {
            User user = userRepository.findById(friend.getFriendInfoId().getFriendId()).get();
            String fileName = fileRepository.findById(user.getFileId()).get().getFileName();
            profileDtos.add(ProfileDto.builder()
                    .userId(user.getId())
                    .fileName(fileName)
                    .nickName(user.getNickName())
                    .statusMsg(user.getStatusMsg())
                    .build());
        }
        return profileDtos;
    }
}
