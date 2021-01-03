package com.clone.chat.domain.base;

import com.clone.chat.code.UserRole;
import com.clone.chat.model.ModelBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserBase extends ModelBase {


	@Id
	@Column(name = "user_id", nullable = false)
	@JsonView({JsonViewApi.class})
	String id;
	String password;
	@JsonView({JsonViewApi.class})
	String nickName;
	@JsonView({JsonViewApi.class})
	String phone;

	@Enumerated(EnumType.STRING)
	@JsonView({JsonViewApi.class})
	UserRole role;

	@Builder
	public UserBase(String id, String password, String nickName, String phone, UserRole role) {
		this.id = id;
		this.password = password;
		this.nickName = nickName;
		this.phone = phone;
		this.role = role;
	}

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
}
