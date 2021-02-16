package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.model.ModelBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
@RedisHash("USER_MESSAGE")
public class UserMessage implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({JsonViewApi.class})
	Long id;

	@JsonView({JsonViewApi.class})
	String userId;

	@JsonView({JsonViewApi.class})
	private Message message;

	@JsonView({JsonViewApi.class})
	Boolean confirm;

	@Builder
	public UserMessage(Long id, String userId, Message message, Boolean confirm) {
		this.id = id;
		this.userId = userId;
		this.message = message;
		this.confirm = confirm;
	}
}
