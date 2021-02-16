package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.model.ModelBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;


@Proxy(lazy = false)
@Getter
@Setter
@NoArgsConstructor
@RedisHash("ROOM")
@NamedEntityGraph(name = "Room.userRooms", attributeNodes = @NamedAttributeNode("userRooms"))
public class Room extends ModelBase implements Serializable, Comparable<Room> {
	
	@Id
	@JsonView({JsonViewApi.class})
	private String id;

	@JsonView({JsonViewApi.class})
	private String name;

	@JsonView({JsonViewApi.class})
	private ZonedDateTime lastMsgDt;

	@JsonView({JsonViewApi.class})
	private String lastMsgContents;

	@JsonManagedReference
	@JsonView({JsonViewApi.class})
	Map<String,RedisUser> users = new HashMap<>();

	@Builder
	public Room(String id, String name, ZonedDateTime lastMsgDt, String lastMsgContents, Map<String,RedisUser> users) {
		this.id = id;
		this.name = name;
		this.lastMsgDt = lastMsgDt;
		this.lastMsgContents = lastMsgContents;
		this.users = users;
	}

	public void addUser(RedisUser user){
		this.users = Optional.ofNullable(this.users).orElseGet(() -> new HashMap<>());
		this.users.put(user.getId(),user);
	}

	@Override
	public int compareTo(Room that) {
		if(this.lastMsgDt == null && that.lastMsgDt != null)
			return 1;
		if(that.lastMsgDt == null)
			return -1;
		return that.lastMsgDt.compareTo(this.lastMsgDt);
	}
}
