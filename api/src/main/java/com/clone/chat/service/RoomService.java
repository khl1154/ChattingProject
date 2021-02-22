package com.clone.chat.service;

import com.clone.chat.domain.User;
import com.clone.chat.repository.UserRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Getter
@Setter
@Transactional(readOnly = true)
@NoArgsConstructor
@Service
public class RoomService {

    @Autowired
    private UserRepository userRepository;

//    @Cacheable(value = "roomInUsers", key = "#roomId")
    public Set<User> getInUsers(Long roomId, Set<String> userIds) {
        return userRepository.findAllById(userIds);
//        Set<User> users = new HashSet<>();
//        for(String userId : userIds) {
//            Optional<User> findUser = userRepository.findById(userId);
//            users.add(findUser.get());
//        }
//        return users;
    }
}
