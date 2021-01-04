package com.clone.chat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.clone.chat.domain.base.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "chatroom")
public class ChatRoom extends BaseTimeEntity{
	
	@Id @Column(name = "chatroom_id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String chatRoomName;

	private String adminId;

	@OneToMany(mappedBy = "chatRoom")
	private List<UserInChatRoom> inUsers;

	@Builder
	public ChatRoom(Long id, String chatRoomName, String adminId) {
		this.id = id;
		this.chatRoomName = chatRoomName;
		this.adminId = adminId;
	}
}
