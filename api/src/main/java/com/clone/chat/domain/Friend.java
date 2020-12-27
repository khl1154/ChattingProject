package com.clone.chat.domain;

import com.clone.chat.domain.base.BaseTimeEntity;
import com.clone.chat.model.ModelBase;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter 
@NoArgsConstructor
@Entity
@Table(name = "friend")
@IdClass(FriendInfoId.class)
public class Friend extends ModelBase {

	//친구 정보
	@Id
	@OneToOne
	@JoinColumn(name = "friend_id")
	private User friend;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Builder
	public Friend(String userId, User userFriend) {
		this.userId = userId;
		this.friend = userFriend;
	}
}
