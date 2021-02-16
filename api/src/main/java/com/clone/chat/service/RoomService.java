package com.clone.chat.service;

import com.clone.chat.domain.Room;
import com.clone.chat.domain.RoomMessage;
import com.clone.chat.redisRepository.RoomMessageRepository;
import com.clone.chat.redisRepository.RoomRepository;
import com.clone.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomMessageRepository roomMessageRepository;

    @Autowired
    UserRepository userRepository;

    public void save(Room room) {
        roomRepository.save(room);
    }

//    @Cacheable(value = "Rooms", key = "#id")
    public List<Room> userRoomFindAllByUserId(String userId) {
        List<Room> rooms = new ArrayList<>();
        Iterable<Room> roomAll = roomRepository.findAll();
        for (Room room : roomAll) {
            if(room.getUsers().containsKey(userId))
                rooms.add(room);
        }
        rooms.sort(null);

        return rooms;
    }

    public List<RoomMessage> getRoomMessages(String roomId) {
        List<RoomMessage> roomMessages = roomMessageRepository.findByRoomId(roomId);
        roomMessages.sort(null);
        return roomMessages;
    }
}
