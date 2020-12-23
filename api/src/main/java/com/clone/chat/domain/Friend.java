package com.clone.chat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {

    @EmbeddedId
    private FriendInfoId friendInfoId;

    @Builder
    public Friend(FriendInfoId friendInfoId) {
        this.friendInfoId = friendInfoId;
    }
}
