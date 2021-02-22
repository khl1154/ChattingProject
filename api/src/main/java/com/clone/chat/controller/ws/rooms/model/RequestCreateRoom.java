package com.clone.chat.controller.ws.rooms.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class RequestCreateRoom {
    private String name;
    private Set<String> users;

    public void addUser(String user){
        this.users = Optional.ofNullable(this.users).orElseGet(() -> new HashSet<>());
        this.users.add(user);
    }
}
