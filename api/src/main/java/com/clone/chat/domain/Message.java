package com.clone.chat.domain;

import javax.persistence.*;


import com.clone.chat.model.ModelBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Getter @Setter
@NoArgsConstructor
@RedisHash("MESSAGE")
public class Message implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({JsonViewApi.class})
	Long id;

	@JsonView({JsonViewApi.class})
	String userId;

	@JsonView({JsonViewApi.class})
	String contents;



//	@JsonIgnore
//	@OneToMany(mappedBy = "message", cascade = CascadeType.ALL) //mappedBy = "room", , cascade = CascadeType.ALL
//	@JsonManagedReference
//	@JsonView({JsonViewApi.class})
//	List<UserMessage> userMessages;
//
//	@JsonIgnore
//	@OneToMany(mappedBy = "message", cascade = CascadeType.ALL) //mappedBy = "room", , cascade = CascadeType.ALL
//	@JsonManagedReference
//	@JsonView({JsonViewApi.class})
//	List<RoomMessage> roomMessages;

	@Column(name = "REG_DT")
	@JsonView({JsonViewApi.class})
	private ZonedDateTime regDt;

//	@Column(name = "UPD_DT")
//	private ZonedDateTime updDt;



	@Builder
	public Message(Long id, String userId, String contents) {
		this.id = id;
		this.userId = userId;
		this.contents = contents;
	}

//	public void addUserMessage(UserMessage message){
//		this.userMessages = Optional.ofNullable(this.userMessages).orElseGet(() -> new ArrayList<>());
//		message.setMessage(this);
//		this.userMessages.add(message);
//	}
//	public void addRoomMessage(RoomMessage message){
//		this.roomMessages = Optional.ofNullable(this.roomMessages).orElseGet(() -> new ArrayList<>());
//		message.setMessage(this);
//		this.roomMessages.add(message);
//	}

	@PrePersist
	public void onPrePersist() {
		if(null == this.regDt) {
			this.regDt = ZonedDateTime.now();
		}
	}

//	@PreUpdate
//	public void onPreUpdate() {
//		this.updDt = ZonedDateTime.now();
//	}
}
