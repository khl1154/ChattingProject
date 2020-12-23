package com.clone.chat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {
    private String userId;
    private String fileName;
    private String nickName;
    private String statusMsg;

    @Builder
    public ProfileDto(String userId, String fileName, String nickName, String statusMsg) {
        this.userId = userId;
        this.fileName = fileName;
        this.nickName = nickName;
        this.statusMsg = statusMsg;
    }
}
