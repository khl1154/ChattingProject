package com.clone.chat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.clone.chat.dto.FileDto;
import com.clone.chat.util.BaseTimeEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseTimeEntity{
	
	@Id @Column(name = "user_id", nullable = false)
	private String id;	//email

	@OneToOne(fetch = FetchType.LAZY)
	private File file;
	private String password;
	private String phone;
	
	@Builder
	public User(String id, FileDto fileDto, String password, String phone) {
		this.id = id;
		this.file = fileDto.toEntity();
		this.password = password;
		this.phone = phone;
	}

}
