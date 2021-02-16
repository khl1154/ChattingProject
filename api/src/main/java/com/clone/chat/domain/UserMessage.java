package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@RedisHash("USER_MESSAGE")
public class UserMessage implements Serializable,Comparable<UserMessage> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({JsonViewApi.class})
	private Long id;

	@JsonView({JsonViewApi.class})
	private String userId;

	@JsonView({JsonViewApi.class})
	private Message message;

	@JsonView({JsonViewApi.class})
	private Boolean confirm;

	@Builder
	public UserMessage(Long id, String userId, Message message, Boolean confirm) {
		this.id = id;
		this.userId = userId;
		this.message = message;
		this.confirm = confirm;
	}

	@Override
	public int compareTo(UserMessage that) {
		if(this.message.getRegDt() == null && that.message.getRegDt() != null)
			return 1;
		if(that.message.getRegDt() == null)
			return -1;
		return this.message.getRegDt().compareTo(that.message.getRegDt());
	}
}
