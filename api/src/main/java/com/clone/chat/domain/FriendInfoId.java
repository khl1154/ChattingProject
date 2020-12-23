package com.clone.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendInfoId implements Serializable {

    private String userId;
    private String friendId;

    public FriendInfoId(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}
