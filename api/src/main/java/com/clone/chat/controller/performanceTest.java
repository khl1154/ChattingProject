package com.clone.chat.controller;

import com.clone.chat.controller.api.anon.AnonApisController;
import com.clone.chat.domain.Message;
import com.clone.chat.domain.RedisUser;
import com.clone.chat.domain.Room;
import com.clone.chat.domain.RoomMessage;
import com.clone.chat.redisRepository.RoomMessageRepository;
import com.clone.chat.redisRepository.RoomRepository;
import com.clone.chat.service.RoomService;
import com.clone.chat.service.WebSocketManagerService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(AnonApisController.URI_PREFIX)
@Slf4j
public class performanceTest {

    private final WebSocketManagerService webSocketManagerService;

    private final RoomRepository roomRepository;

    private final RoomMessageRepository roomMessageRepository;

    private final RoomService roomService;

    public static final String URI_PREFIX = "/anon-apis";

    // 메시지 전송
    @GetMapping("/test")
    public void test(String userId, Long roomId) {

        Message msg = Message.builder().userId(userId).contents("1").build();

        Room room = roomRepository.findById(roomId).get();
        room.setLastMsgContents("1");
        roomRepository.save(room);
        RoomMessage roomMessage = RoomMessage.builder()
                .roomId(roomId)
                .message(msg)
                .confirm(false).build();
        for (String userId2 : room.getUsers().keySet()) {
            roomMessage.setUserId(userId2);
            roomMessageRepository.save(roomMessage);
            webSocketManagerService.sendToUserByUserId("/queue" + URI_PREFIX + "/" + roomId + "/message", roomMessage, userId2);
            List<Room> rooms = roomService.userRoomFindAllByUserId(userId2);
            webSocketManagerService.sendToUserByUserId("/queue/rooms", rooms, userId2);
        }
    }

    // 메시지 전체 조회
    @GetMapping("/test2")
    public void test2(String userId, Long roomId) {
        List<RoomMessage> roomMessages = roomService.getRoomMessages(roomId);
    }

}
