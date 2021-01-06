package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.domain.base.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@SequenceGenerator(
		name = "CHAT_SEQ_GENERATOR",
		sequenceName = "CHAT_SEQ", // 매핑할 시퀀스 이름
		initialValue = 1, allocationSize = 1)
@Table(name = "chat_message")
public class ChatMessage extends BaseTimeEntity {

	@Id @Column(name = "chat_message_id", nullable = false)
	@GeneratedValue
	Long id;
	String userId;
	String text;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
			generator = "CHAT_SEQ_GENERATOR")
	String chatSeq;


	//chat message
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setchatSeq(String chatSeq) {
		this.chatSeq = chatSeq;
	}
}
