package com.clone.chat.domain;

import javax.persistence.*;


import com.clone.chat.model.ModelBase;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "MESSAGE")
public class Message extends ModelBase {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Column(name = "USER_ID")
	String userId;

	@Column(name = "CONTENTS")
	String contents;


	@OneToMany(mappedBy = "message", cascade = CascadeType.ALL) //mappedBy = "room", , cascade = CascadeType.ALL
	List<UserMessage> userMessages;

	@OneToMany(mappedBy = "message", cascade = CascadeType.ALL) //mappedBy = "room", , cascade = CascadeType.ALL
	List<RoomMessage> roomMessages;


	@Builder
	public Message(Long id, String userId, String contents) {
		this.id = id;
		this.userId = userId;
		this.contents = contents;
	}

	public void addUserMessage(UserMessage message){
		this.userMessages = Optional.ofNullable(this.userMessages).orElseGet(() -> new ArrayList<>());
		message.setMessage(this);
		this.userMessages.add(message);
	}
	public void addRoomMessage(RoomMessage message){
		this.roomMessages = Optional.ofNullable(this.roomMessages).orElseGet(() -> new ArrayList<>());
		message.setMessage(this);
		this.roomMessages.add(message);
	}
}
