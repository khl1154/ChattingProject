package com.clone.chat.service;

import com.clone.chat.code.MsgCode;
import com.clone.chat.domain.File;
import com.clone.chat.domain.Friend;
import com.clone.chat.domain.FriendInfoId;
import com.clone.chat.domain.User;
import com.clone.chat.dto.FriendDto;
import com.clone.chat.dto.ProfileDto;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.exception.ErrorTrace;
import com.clone.chat.repository.FileRepository;
import com.clone.chat.repository.FriendRepository;
import com.clone.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void saveFriend(FriendDto friendDto) {
        Optional<User> userOptional = userRepository.findById(friendDto.getUserId());
        Optional<User> friendOptional = userRepository.findById(friendDto.getFriendId());
        if(!userOptional.isPresent() || !friendOptional.isPresent()) {
            throw new BusinessException(MsgCode.ERROR_ENTITY_NOT_FOUND, ErrorTrace.getName());
        }
        // 사용자 id와 등록할 친구 id가 같은 경우
        if(userOptional.get().getId() == friendOptional.get().getId()) {
            throw new BusinessException(MsgCode.ERROR_INVALID_FRIEND_RELATIONSHIP, ErrorTrace.getName());
        }
        User friendInfo = friendOptional.get();
        Friend friend = Friend.builder()
                .userId(friendDto.getUserId())
                .userFriend(friendInfo)
                .build();
        friendRepository.save(friend);
    }

    public List<ProfileDto> getList(String userId) {
        // 유저 아이디에 해당하는 친구 아이디를 다 가져온다.
        List<Friend> friends = friendRepository.findAllByUserId(userId);
        // 친구 아이디에 해당하는 유저 정보를 다 가져온다.
        List<ProfileDto> profileDtos = new LinkedList<>();
        for(Friend friend : friends) {
            String profileImagePath = "";
            if(friend.getFriend().getFile() != null)
                profileImagePath = friend.getFriend().getFile().getFilePath();

            profileDtos.add(ProfileDto.builder()
                    .userId(friend.getFriend().getId())
                    .filePath(profileImagePath)
                    .nickName(friend.getFriend().getNickName())
                    .statusMsg(friend.getFriend().getStatusMsg())
                    .build());
        }
        return profileDtos;
    }
}
