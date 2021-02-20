package com.clone.chat.controller.ws.rooms.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class RequestCreateRoom {
    private String name;
    private List<String> users;

    public void addUser(String user){
        this.users = Optional.ofNullable(this.users).orElseGet(() -> new ArrayList<>());
        this.users.add(user);
    }
}
