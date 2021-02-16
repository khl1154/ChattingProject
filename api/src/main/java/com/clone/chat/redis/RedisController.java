package com.clone.chat.redis;

import com.clone.chat.domain.UserRoom;
import com.clone.chat.redisRepository.UserRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class RedisController {

    @Autowired
    TestRepository testRepository;

    @Autowired
    UserRoomRepository userRoomRepository;

    @GetMapping("/anon-apis/redis")
    public Test testRedis() {
        Test test = new Test();
        test.setId(1L);
        Test2 test2 = new Test2();
        Test2 test21 = new Test2();
        test.getTest().add(test2);
        test.getTest().add(test21);
        testRepository.save(test);
        Optional<Test> byId = testRepository.findById(1L);
        return byId.get();

    }
}
