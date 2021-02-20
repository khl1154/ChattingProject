package com.clone.chat.controller.ws.messages.model;

import com.clone.chat.model.ModelBase;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class RequestMessage extends ModelBase {
    private String contents;
    private ZonedDateTime sendDt;
}
