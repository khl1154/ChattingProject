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
public class Friend extends ModelBase {

	@Id
	@Column (name = "SEQ")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column (name = "USER_ID")
	private String userId;

	@Column (name = "FRIENDS_ID")
	private String friendsId;

	@Builder
	public Friend(Long seq, String userId, String friendsId) {
		this.seq = seq;
		this.userId = userId;
		this.friendsId = friendsId;
	}
}
