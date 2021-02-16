package com.clone.chat.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class ChatRoomListDto {
    private Long chatRoomId;
    private String chatRoomName;
    private String adminId;
    private int inUserCount;
    private List<ProfileDto> profiles;

    @Builder
    public ChatRoomListDto(Long chatRoomId, String chatRoomName, String adminId, int inUserCount, List<ProfileDto> profiles) {
        this.chatRoomId = chatRoomId;
        this.chatRoomName = chatRoomName;
        this.adminId = adminId;
        this.inUserCount = inUserCount;
        this.profiles = profiles;
    }
}
