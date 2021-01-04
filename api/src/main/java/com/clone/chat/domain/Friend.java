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

	@Id
	@Column(name = "user_id")
	private String userId;

	@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "friend_id")
	private User friend;


	@Builder
	public Friend(String userId, User userFriend) {
		this.userId = userId;
		this.friend = userFriend;
	}
}
