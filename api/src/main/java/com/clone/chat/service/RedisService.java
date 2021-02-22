package com.clone.chat.service;

import com.clone.chat.domain.Message;
import com.clone.chat.domain.Room;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@NoArgsConstructor
public class RedisService {

    private static final String ROOMS = "ROOMS";
    private static final String USER_IN_ROOMS = "USER_IN_ROOMS";
    private static final String ROOM_IN_USERS = "ROOM_IN_USERS";
    private static final String ROOM_MESSAGES = "ROOM_MESSAGES";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, Room> rooms;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Set<Room>> userInRooms;

//    @Resource(name = "redisTemplate")
//    private HashOperations<String, Long, List<String>> roomInUsers;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, List<Message>> roomMessages;

    public Room findRoom(Long roomId) {
        return rooms.get(ROOMS, roomId);
    }

    public void saveRoom(Room room) {
        rooms.put(ROOMS, room.getId(), room);
    }

    public void joinRoom(String userId, Room room) {
        Set<Room> userInRoom = userInRooms.get(USER_IN_ROOMS, userId);
        if(userInRoom == null) userInRoom = new HashSet<>();
        userInRoom.add(room);
        userInRooms.put(USER_IN_ROOMS, userId, userInRoom);
    }

    public List<Room> getUserInRooms(String userId) {
        Set<Room> members = userInRooms.get(USER_IN_ROOMS, userId);
        if(members == null) return new ArrayList<>();
        List<Room> roomList = new ArrayList(members);
        roomList.sort(null);
        return roomList;
    }

    public void sendMessage(Long roomId, Message message) {
        List<Message> messages = roomMessages.get(ROOM_MESSAGES, roomId);
        if(messages == null) messages = new ArrayList<>();
        messages.add(message);
        roomMessages.put(ROOM_MESSAGES, roomId, messages);
    }

    public List<Message> getMessages(Long roomId) {
        List<Message> messages = roomMessages.get(ROOM_MESSAGES, roomId);
        if(messages == null) return new ArrayList<>();
        return messages;
    }

}

