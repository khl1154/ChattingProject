package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.model.ModelBase;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ROOM")
public class Room extends ModelBase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;
//	private Long msgCount;
//	private String admin;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL) //mappedBy = "room", , cascade = CascadeType.ALL
//	@JoinColumn(name = "ROOM_IDS", referencedColumnName = "ID", insertable = true, updatable = true)
	List<UserRoom> userRooms;

	@Builder
	public Room(Long id, String name, List<UserRoom> userRooms) {
		this.id = id;
		this.name = name;
		this.userRooms = userRooms;
	}



	public void addUserRoom(UserRoom userRoom){
		this.userRooms = Optional.ofNullable(this.userRooms).orElseGet(() -> new ArrayList<>());
		userRoom.setRoom(this);
		this.userRooms.add(userRoom);
	}
}
