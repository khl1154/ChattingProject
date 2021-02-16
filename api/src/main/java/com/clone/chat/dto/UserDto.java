package com.clone.chat.dto;

import com.clone.chat.domain.User;
import com.clone.chat.domain.base.UserBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String id;
    private String password;
    private String nickName;
    private String phone;
    private String statusMsg;

    public User toEntity() {
        return UserBase.builder()
                .id(id)
                .password(password)
                .nickName(nickName)
                .phone(phone)
                .statusMsg(statusMsg)
                .build()
                .map(User.class);
    }
}
