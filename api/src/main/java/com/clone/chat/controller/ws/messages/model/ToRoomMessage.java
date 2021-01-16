package com.clone.chat.controller.ws.messages.model;

import com.clone.chat.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToRoomMessage extends ModelBase {

    Long roomId;
    String contents;


}
