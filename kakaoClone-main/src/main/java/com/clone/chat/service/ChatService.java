package com.clone.chat.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.clone.chat.dto.ChatRoomDto;

public interface ChatService {

	/*
	 * 채팅방 생성
	 * - 방 이름 (중복 허용)
	 */
	Long chatRoomCreate(ChatRoomDto dto);

	/*
	 * 채팅방 목록 반환
	 */
	List<ChatRoomDto.Response> getList(String userId, String search);
	
	/*
	 * 초대
	 */
	void invite(List<String> users, Long chatRoomId);
}
