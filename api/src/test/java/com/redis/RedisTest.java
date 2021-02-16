package com.redis;


import com.clone.chat.ChatApplication;
import com.clone.chat.domain.Message;
import com.clone.chat.redis.TestRepository;
import com.clone.chat.redisRepository.MessageRepository;
import com.clone.chat.redisRepository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("dev")
public class RedisTest {

    @Autowired
    TestRepository testRepository;

    @Autowired
    RoomRepository repository;

    @Rollback(value = false)
    @Test
    @Transactional
    public void redisHash_Insert() throws Exception {
        //given
        Long id = 1L;
        com.clone.chat.redis.Test test = new com.clone.chat.redis.Test();
        test.setId(id);
        testRepository.save(test);
        //when

        //then
    }
}
