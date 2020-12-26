package com.clone.chat.dto;

import com.clone.chat.domain.Friend;
import com.clone.chat.domain.FriendInfoId;
import com.clone.chat.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
public class FriendDto {

    @NotEmpty(message = "userId가 존재하지 않습니다.")
    private String userId;
    @NotEmpty(message = "friendId가 존재하지 않습니다.")
    private String friendId;


}
