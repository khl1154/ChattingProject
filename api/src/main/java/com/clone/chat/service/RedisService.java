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
    private static final String USER_MESSAGES = "USER_MESSAGES";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, Room> rooms;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, Set<Long>> userInRooms;

    @Resource(name = "redisTemplate")
    private HashOperations<String, Long, List<Message>> roomMessages;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Long> oneToOneRoom;

    public Room findRoom(Long roomId) {
        return rooms.get(ROOMS, roomId);
    }

    public void saveRoom(Room room) { rooms.put(ROOMS, room.getId(), room); }

    public void saveUserInRoom(String userId, Long roomId) {
        Set<Long> members = userInRooms.get(USER_IN_ROOMS, userId);
        if(members == null) members = new HashSet<>();
        members.add(roomId);
    }

    public void joinRoom(String userId, Room room) {
        Set<Long> userInRoom = userInRooms.get(USER_IN_ROOMS, userId);
        if(userInRoom == null) userInRoom = new HashSet<>();
        userInRoom.add(room.getId());
        userInRooms.put(USER_IN_ROOMS, userId, userInRoom);
    }

    public List<Room> getUserInRooms(String userId) {
        Set<Long> members = userInRooms.get(USER_IN_ROOMS, userId);
        if(members == null) return new ArrayList<>();
        List<Room> roomList = new ArrayList<>();
        for(Long roomId : members) {
            roomList.add(rooms.get(ROOMS,roomId));
        }
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

    public Long getOneToOneRoom(String user1, String user2) {
        return oneToOneRoom.get(user1+":"+user2);
    }

    public void createOneToOneRoom(String user1, String user2, Long roomId) {
        oneToOneRoom.set(user1+":"+user2,roomId);
    }
}

