package com.clone.chat.domain;

import com.clone.chat.domain.base.UserBase;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.model.view.json.JsonViewFrontEnd;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RedisUser implements Serializable {

    @JsonView({JsonViewApi.class})
    String id;

    @JsonView({JsonViewApi.class})
    String nickName;

    @JsonView({JsonViewApi.class})
    String statusMsg;

    @JsonView({JsonViewApi.class})
    String filePath;

    @Builder
    public RedisUser(String id, String nickName, String statusMsg, String filePath) {
        this.id = id;
        this.nickName = nickName;
        this.statusMsg = statusMsg;
        this.filePath = filePath;
    }
}

