package com.clone.chat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(FriendInfoId.class)
public class Friend {

    //친구 정보
    @Id
    @OneToOne
    @JoinColumn(name = "friend_id")
    private User friend;

    @Id
    @Column(name = "user_id")
    private String userId;

    @Builder
    public Friend(String userId, User userFriend) {
        this.userId = userId;
        this.friend = userFriend;
    }
}
