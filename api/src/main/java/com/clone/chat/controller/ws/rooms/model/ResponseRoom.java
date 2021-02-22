package com.clone.chat.controller.ws.rooms.model;

import com.clone.chat.domain.Room;
import com.clone.chat.domain.User;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ResponseRoom {

    @JsonView({JsonViewApi.class})
    private Room room;
    @JsonView({JsonViewApi.class})
    private Set<User> roomInUsers;

    @Builder
    public ResponseRoom(Room room, Set<User> roomInUsers) {
        this.room = room;
        this.roomInUsers = roomInUsers;
    }
}
