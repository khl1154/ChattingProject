package com.clone.chat.dto;

import com.clone.chat.domain.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
	String id;
	String pw;
	String nickName;
	String phone;
	
	public User toEntity() {
		return User.builder()
			.id(id)
			.password(pw)
			.nickName(nickName)
			.phone(phone)
			.build();
	}
}
