package com.clone.chat.dto;

import com.clone.chat.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String id;
    private Long fileId;
    private String password;
    private String nickName;
    private String phone;
    private String statusMsg;

    public User toEntity() {
        return User.builder()
                .id(id)
                .password(password)
                .fileId(fileId)
                .nickName(nickName)
                .phone(phone)
                .statusMsg(statusMsg)
                .build();
    }
}
