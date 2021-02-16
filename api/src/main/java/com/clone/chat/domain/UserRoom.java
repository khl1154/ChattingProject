package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.model.ModelBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.hibernate.annotations.Proxy;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Proxy(lazy = false)
@Getter  @Setter
@NoArgsConstructor
@RedisHash("USER_ROOM")
public class UserRoom extends ModelBase implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@JsonView({JsonViewApi.class})
	Long id;

//	@Column(name="ROOM_ID")
//	Long roomId;

	@Column(name="USER_ID")
	@JsonView({JsonViewApi.class})
	String userId;

	@JsonIgnore
//	@JoinColumns(@JoinColumn(name = "ROOM_IDS"))
//	@JoinTable(name = "ROOM",)
	@ManyToOne
	@JoinColumn(name = "ROOM_ID") //, nullable = false, insertable = false, updatable = false
	@JsonBackReference
	@JsonView({JsonViewApi.class})
	private Long roomId;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	@JsonView({JsonViewApi.class})
	private User user;

	@Builder
	public UserRoom(Long id, Long roomId, String userId) {
		this.id = id;
		this.roomId = roomId;
		this.userId = userId;
		this.user = user;
	}

}
	
