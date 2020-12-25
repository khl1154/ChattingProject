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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User extends UserBase {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    @Builder
    public User(String id, String password, String nickName, String phone, String statusMsg, File file) {
        super(id, password, nickName, phone, statusMsg);
        this.file = file;
    }
}
