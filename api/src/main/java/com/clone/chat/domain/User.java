package com.clone.chat.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.clone.chat.domain.base.BaseTimeEntity;

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
	String id;	//email
	String password;
	String nickName;
	String phone;
	
	@Builder
	public User(String id, String password, String nickName, String phone) {
		this.id = id;
		this.password = password;
		this.nickName = nickName;
		this.phone = phone;
	}
	
	
}
