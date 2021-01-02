package com.clone.chat.domain;

import com.clone.chat.domain.base.UserBase;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends UserBase {

}
