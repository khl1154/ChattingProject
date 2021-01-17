package com.clone.chat.controller.ws.rooms.model;

import com.clone.chat.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSendRoomMessage extends ModelBase {
    Long roomId;
    String contents;
}
