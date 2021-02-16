package com.clone.chat.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@RedisHash("TEST")
public class Test2 {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;
}
