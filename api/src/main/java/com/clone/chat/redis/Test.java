package com.clone.chat.redis;

import com.clone.chat.domain.RoomMessage;
import com.clone.chat.domain.UserRoom;
import com.clone.chat.model.view.json.JsonViewApi;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RedisHash("TEST")
public class Test {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    Long id;

    List<Test2> test = new ArrayList<>();

}
