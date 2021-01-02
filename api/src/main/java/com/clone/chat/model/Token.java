package com.clone.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Token extends ModelBase{

    public String name;
    private String token;

    @Builder
    public Token(String name, String token) {
        this.name = name;
        this.token = token;
    }
}
