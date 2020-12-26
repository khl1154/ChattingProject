package com.clone.chat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Token extends ModelBase{

    private String token;

    public Token(String token) {
        this.token = token;
    }
}
