package com.clone.chat.redis;

import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Getter
@Setter
@RedisHash("TEST")
public class Test3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonView({JsonViewApi.class})
    private Long id;

    @Column(name = "NAME")
    @JsonView({JsonViewApi.class})
    private String name;
//	private Long msgCount;
//	private String admin;

    @Column(name = "LAST_MSG_DT")
    @JsonView({JsonViewApi.class})
    private ZonedDateTime lastMsgDt;

    @Column(name = "LAST_MSG_CONTENTS")
    @JsonView({JsonViewApi.class})
    private String lastMsgContents;
}
