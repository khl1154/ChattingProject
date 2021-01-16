package com.clone.chat.domain;

import javax.persistence.*;

import com.clone.chat.model.ModelBase;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "USER_MESSAGE")
public class UserMessage extends ModelBase {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Long id;

//	@Column(name = "MESSAGE_ID")
//	String messageId;

	@Column(name = "USER_ID")
	String userId;

	@ManyToOne
	@JoinColumn(name = "MESSAGE_ID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
	private Message message;



	@Builder
	public UserMessage(Long id, String userId) {
		this.id = id;
		this.userId = userId;
	}
}
