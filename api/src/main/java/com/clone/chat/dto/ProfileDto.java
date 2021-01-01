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
    private String filePath;
    private String nickName;
    private String statusMsg;

    @Builder
    public ProfileDto(String userId, String filePath, String nickName, String statusMsg) {
        this.userId = userId;
        this.filePath = filePath;
        this.nickName = nickName;
        this.statusMsg = statusMsg;
    }
}
