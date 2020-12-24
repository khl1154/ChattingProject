//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.clone.chat.domain.base;
import com.clone.chat.model.ModelBase;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class UserBase extends ModelBase{
	@Id
	@Column(name = "user_id", nullable = false)
	String id;
	String password;
	String nickName;
	String phone;
	String statusMsg;
	@CreatedDate
	private LocalDateTime createdDate;
	@LastModifiedDate
	private LocalDateTime modifiedDate;

	public UserBase(String id, String password, String nickName, String phone, String statusMsg) {
		this.id = id;
		this.password = password;
		this.nickName = nickName;
		this.phone = phone;
		this.statusMsg = statusMsg;
	}

	public LocalDateTime getCreatedDate() {
		return this.createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return this.modifiedDate;
	}

	public String getId() {
		return this.id;
	}

	public String getPassword() {
		return this.password;
	}

	public String getNickName() {
		return this.nickName;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getStatusMsg() {
		return this.statusMsg;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public void setNickName(final String nickName) {
		this.nickName = nickName;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public void setStatusMsg(final String statusMsg) {
		this.statusMsg = statusMsg;
	}

	public void setCreatedDate(final LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public void setModifiedDate(final LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public UserBase() {
	}
}
