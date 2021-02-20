package com.clone.chat.domain;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash
public class UserInChatRoom implements Serializable {

    @Id
    private Long id;
    @Indexed
    private Long roomId;
    @Indexed
    private String userId;

    @Builder
    public UserInChatRoom(Long roomId, String userId) {
        this.roomId = roomId;
        this.userId = userId
        ;
    }
}
