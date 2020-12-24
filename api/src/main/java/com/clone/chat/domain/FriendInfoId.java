package com.clone.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendInfoId implements Serializable {

    @Column(name = "user_id")
    private String userId;
    @Column(name = "friend_id")
    private String friendId;

    public FriendInfoId(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
}
