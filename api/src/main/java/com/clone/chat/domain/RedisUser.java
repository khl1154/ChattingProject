package com.clone.chat.domain;

import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RedisUser implements Serializable {

    @JsonView({JsonViewApi.class})
    private String id;

    @JsonView({JsonViewApi.class})
    private String nickName;

    @JsonView({JsonViewApi.class})
    private String statusMsg;

    @JsonView({JsonViewApi.class})
    private String filePath;

    @Builder
    public RedisUser(String id, String nickName, String statusMsg, String filePath) {
        this.id = id;
        this.nickName = nickName;
        this.statusMsg = statusMsg;
        this.filePath = filePath;
    }
}

