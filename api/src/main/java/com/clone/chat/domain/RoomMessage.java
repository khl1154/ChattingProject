package com.clone.chat.domain;

import com.clone.chat.model.ModelBase;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ROOM_MESSAGE")
public class RoomMessage extends ModelBase {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

	@Column(name = "ROOM_ID")
	Long roomId;

	@Column(name = "USER_ID")
	String userId;


	@ManyToOne
	@JoinColumn(name = "MESSAGE_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	private Message message;

	@Builder
	public RoomMessage(Long id, Long roomId, String userId, Message message) {
		this.id = id;
		this.roomId = roomId;
		this.userId = userId;
		this.message = message;
	}
}
