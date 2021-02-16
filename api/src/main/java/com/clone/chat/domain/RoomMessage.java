package com.clone.chat.domain;

import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.*;
import java.io.Serializable;


@Getter @Setter
@NoArgsConstructor
@RedisHash("ROOM_MESSAGE")
public class RoomMessage implements Serializable, Comparable<RoomMessage> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({JsonViewApi.class})
	private Long id;

	@JsonView({JsonViewApi.class})
	private String userId;

	@Indexed
	@JsonView({JsonViewApi.class})
	private String roomId;

	@JsonView({JsonViewApi.class})
	private Message message;

	@JsonView({JsonViewApi.class})
	private Boolean confirm;

	@Builder
	public RoomMessage(Long id, String roomId, Message message, Boolean confirm) {
		this.id = id;
		this.roomId = roomId;
		this.message = message;
		this.confirm = confirm;
	}

	@Override
	public int compareTo(RoomMessage that) {
		if(this.message.getRegDt() == null && that.message.getRegDt() != null)
			return 1;
		if(that.message.getRegDt() == null)
			return -1;
		return this.message.getRegDt().compareTo(that.message.getRegDt());
	}
}
