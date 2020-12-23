package com.clone.chat.dto;

import com.clone.chat.domain.Friend;
import com.clone.chat.domain.FriendInfoId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FriendDto {

    private String userId;
    private String friendId;

    public Friend toEntity() {
        return Friend.builder()
                .friendInfoId(new FriendInfoId(userId,friendId))
                .build();
    }

}
