package com.clone.chat.controller;

import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.api.anon.AnonApisController;
import com.clone.chat.controller.ws.rooms.model.ResponseRoom;
import com.clone.chat.domain.Message;
import com.clone.chat.domain.Room;
import com.clone.chat.domain.RoomMessage;
import com.clone.chat.domain.User;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.model.UserToken;
import com.clone.chat.service.RedisService;
import com.clone.chat.service.RoomService;
import com.clone.chat.service.WebSocketManagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(AnonApisController.URI_PREFIX)
@Slf4j
public class performanceTest {

//    private final WebSocketManagerService webSocketManagerService;

    public static final String URI_PREFIX = "/anon-apis";

    private final RedisService redisService;

    private final RoomService roomService;
    // 메시지 전송
//    @GetMapping("/test")
//    public void test(String userId, Long roomId) {
//
//        Message msg = Message.builder().userId(userId).contents("테스트메시지").build();
//
//        Room room = redisService.findRoom(roomId);
//        room.setLastMsgContents("테스트메시지");
//        redisService.saveRoom(room);
//        redisService.sendMessage(room.getId(), msg);
//
//        for(String userId1 : room.getInUserIds()) {
//            webSocketManagerService.sendToUserByUserId("/queue"+URI_PREFIX+"/"+roomId+"/message", msg, userId1);
//
//            List<Room> rooms = redisService.getUserInRooms(userId1);
//            List<ResponseRoom> responseRooms = new ArrayList<>();
//            for (Room room1 : rooms) {
//                Set<User> roomInUsers1 = roomService.getInUsers(room1.getId(), room1.getInUserIds());
//                ResponseRoom responseRoom = ResponseRoom.builder()
//                        .room(room1)
//                        .roomInUsers(roomInUsers1).build();
//                responseRooms.add(responseRoom);
//            }
//            webSocketManagerService.sendToUserByUserId("/queue/rooms", responseRooms, userId1);
//        }
//    }

//    // 메시지 전체 조회
//    @GetMapping("/test2")
//    public void test2(String userId, Long roomId) {
//        List<RoomMessage> roomMessages = roomService.getRoomMessages(roomId);
//    }
//
//    // 메시지 전체 조회
//    @GetMapping("/test3")
//    public void test3(String userId) {
//        List<Room> rooms = roomService.userRoomFindAllByUserId(userId);
//    }

}
