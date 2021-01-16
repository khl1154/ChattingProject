package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.model.ModelBase;
import lombok.*;

@Getter  @Setter
@NoArgsConstructor
@Entity
@Table(name = "user_room")
public class UserRoom extends ModelBase {

	@Id
	@GeneratedValue(strategy= GenerationType.SEQUENCE)
	Long id;

//	@Column(name="ROOM_ID")
//	Long roomId;

	@Column(name="USER_ID")
	String userId;

	@ManyToOne
//	@JoinColumns(
//			@JoinColumn(name = "ROOM_IDS")
//	)
	@JoinColumn(name = "ROOM_ID") //, nullable = false, insertable = false, updatable = false
//	@JoinTable(name = "ROOM",)
	private Room room;

	@ManyToOne
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	private User user;

	@Builder
	public UserRoom(Long id, Long roomId, String userId, Room room, User user) {
		this.id = id;
//		this.roomId = roomId;
		this.userId = userId;
		this.room = room;
		this.user = user;
	}

	//	@Column(columnDefinition="BOOLEAN DEFAULT false")
//	private boolean inOutStatus;

}
	
