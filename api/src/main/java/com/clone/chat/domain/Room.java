package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.model.ModelBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Index;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.*;


@Getter
@Setter
@NoArgsConstructor
public class Room extends ModelBase implements Serializable, Comparable<Room> {
	
	@Id
	@JsonView({JsonViewApi.class})
	private Long id;

	@JsonView({JsonViewApi.class})
	private String name;

	@JsonView({JsonViewApi.class})
	private ZonedDateTime lastMsgDt;

	@JsonView({JsonViewApi.class})
	private String lastMsgContents;

	@JsonView({JsonViewApi.class})
	private Set<String> inUserIds;


	@Builder
	public Room(Long id, String name, ZonedDateTime lastMsgDt, String lastMsgContents, Set<String> inUserIds) {
		this.id = id;
		this.name = name;
		this.lastMsgDt = lastMsgDt;
		this.lastMsgContents = lastMsgContents;
		this.inUserIds = inUserIds;
	}

	@Override
	public int compareTo(Room that) {
		if(this.lastMsgDt == null && that.lastMsgDt != null)
			return 1;
		if(that.lastMsgDt == null)
			return -1;
		return that.lastMsgDt.compareTo(this.lastMsgDt);
	}

	public void addInUser(String userId) {
		if(inUserIds == null) inUserIds = new HashSet<>();
		inUserIds.add(userId);
	}
}
