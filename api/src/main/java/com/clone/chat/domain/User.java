package com.clone.chat.domain;

import com.clone.chat.domain.base.UserBase;
import com.clone.chat.dto.FileDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends UserBase {

    @Builder
    public User(String id, Long fileId, String password, String nickName, String phone, String statusMsg) {
        super(id, fileId, password, nickName, phone, statusMsg);
    }
}
