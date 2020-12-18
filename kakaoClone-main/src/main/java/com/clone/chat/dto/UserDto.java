package com.clone.chat.dto;

import com.clone.chat.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
	private String id;
	private FileDto fileDto;
	private String pw;
	private String phone;
	
	public User toEntity() {
		return User.builder()
			.id(id)
			.fileDto(fileDto)
			.password(pw)
			.phone(phone)
			.build();
	}

}
