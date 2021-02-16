package com.clone.chat.domain;

import com.clone.chat.model.ModelBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Proxy;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
@RedisHash("ROOM_MESSAGE")
public class RoomMessage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({JsonViewApi.class})
	Long id;

	@JsonView({JsonViewApi.class})
	String userId;

	@Indexed
	@JsonView({JsonViewApi.class})
	private String roomId;

	@JsonView({JsonViewApi.class})
	Message message;

	@JsonView({JsonViewApi.class})
	Boolean confirm;

	@Builder
	public RoomMessage(Long id, String roomId, Message message, Boolean confirm) {
		this.id = id;
		this.roomId = roomId;
		this.message = message;
		this.confirm = confirm;
	}
}
