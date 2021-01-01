package com.clone.chat.model;

import com.clone.chat.domain.ChatRoom;
import com.clone.chat.domain.UserInChatRoom;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomDto {

    @ApiModelProperty(notes = "방이름")
    String chatRoomName;
    @ApiModelProperty(notes = "사용자이이디")
    String userId;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .chatRoomName(chatRoomName)
                .adminId(userId)
                .build();
    }


    private String name;
    private String message;

    public ChatRoomDto(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}