package com.clone.chat.domain;

import com.clone.chat.domain.base.UserBase;
import com.clone.chat.model.view.json.JsonViewFrontEnd;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER")
@NamedEntityGraph(name = "User.friends", attributeNodes = @NamedAttributeNode("friends"))
public class User extends UserBase {

    @Transient
    private String token;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_FRIEND")
    @JsonView({JsonViewFrontEnd.class})
    private List<User> friends = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id")
    private File file;

    public void addFirend(User... users) {
        if(null == friends) {
            friends = new ArrayList<>();
        }
        friends.addAll(Arrays.asList(users));
    }
}
