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
public class Message implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView({JsonViewApi.class})
	private Long id;

	@JsonView({JsonViewApi.class})
	private String userId;

	@JsonView({JsonViewApi.class})
	private String contents;

	@JsonView({JsonViewApi.class})
	private ZonedDateTime regDt;

	@Builder
	public Message(Long id, String userId, String contents, ZonedDateTime regDt) {
		this.id = id;
		this.userId = userId;
		this.contents = contents;
		this.regDt = regDt;
	}

	@PrePersist
	public void onPrePersist() {
		if(null == this.regDt) {
			this.regDt = ZonedDateTime.now();
		}
	}
}
