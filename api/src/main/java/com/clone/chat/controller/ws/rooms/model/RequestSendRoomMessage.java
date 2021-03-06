package com.clone.chat.controller.ws.rooms.model;

import com.clone.chat.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class RequestSendRoomMessage extends ModelBase {
    private Long roomId;
    private String contents;
    private ZonedDateTime sendDt;
}
