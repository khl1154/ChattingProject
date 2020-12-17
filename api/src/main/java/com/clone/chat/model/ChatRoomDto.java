package com.clone.chat.model;

import java.time.LocalDateTime;

import com.clone.chat.domain.ChatRoom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class ChatRoomDto {

	String chatRoomName;
	String userId;
	
	public ChatRoom toEntity() {
		return ChatRoom.builder()
				.name(chatRoomName)
				.admin(userId)
				.build();
	}

	@Getter
	@Setter
	public static class Response {
		Long chatRoomId;
		String chatRoomName;
		LocalDateTime modifiedDate;
		Long unreadMsgCount;
		String lastMsg;
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
