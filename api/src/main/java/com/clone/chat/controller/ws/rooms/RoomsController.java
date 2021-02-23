package com.clone.chat.controller.ws.rooms;

import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.ws.rooms.model.RequestSendRoomMessage;
import com.clone.chat.controller.ws.rooms.model.RequestCreateRoom;
import com.clone.chat.controller.ws.rooms.model.ResponseRoom;
import com.clone.chat.domain.*;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.model.UserToken;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.domain.Message;
import com.clone.chat.domain.Room;
import com.clone.chat.repository.*;
import com.clone.chat.service.RedisService;
import com.clone.chat.service.RoomService;
import com.clone.chat.service.UserService;
import com.clone.chat.service.WebSocketManagerService;
import com.fasterxml.jackson.annotation.JsonView;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.*;

@Controller("ws-rooms-controller")
@Slf4j
@RequiredArgsConstructor
public class RoomsController {
    public static final String URI_PREFIX = "/rooms";

    private final WebSocketManagerService webSocketManagerService;

    private final UserService userService;

    private final RoomIdRepository roomIdRepository;

    private final RedisService redisService;

    private final RoomService roomService;

    @MessageMapping(URI_PREFIX+"/create-room")
    public void createRoom(RequestCreateRoom createRoom, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        createRoom.addUser(user.getId());

        Room room = Room.builder().name(createRoom.getName()).inUserIds(createRoom.getUsers()).build();
        RoomId roomId = roomIdRepository.save(new RoomId());
        room.setId(roomId.getId());
        redisService.saveRoom(room);

        for(String userId : createRoom.getUsers()) {
            redisService.joinRoom(userId, room);
            List<ResponseRoom> responseRooms = new ArrayList<>();
            List<Room> rooms = redisService.getUserInRooms(userId);

            for (Room room1 : rooms) {
                Set<User> roomInUsers = roomService.getInUsers(room1.getId(), room1.getInUserIds());
                ResponseRoom responseRoom = ResponseRoom.builder()
                        .room(room1)
                        .roomInUsers(roomInUsers).build();
                responseRooms.add(responseRoom);
            }
            webSocketManagerService.sendToUserByUserId("/queue/rooms", responseRooms, userId);
        }
    }

    @MessageMapping(URI_PREFIX)
    @SendToUser("/queue"+URI_PREFIX)
    @JsonView({JsonViewApi.class})
    public List<ResponseRoom> processMessageFromClient(Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        List<Room> rooms = redisService.getUserInRooms(user.getId());

        List<ResponseRoom> responseRooms = new ArrayList<>();
        for (Room room : rooms) {
            Set<User> roomInUsers = roomService.getInUsers(room.getId(), room.getInUserIds());
            ResponseRoom responseRoom = ResponseRoom.builder()
                    .room(room)
                    .roomInUsers(roomInUsers).build();
            responseRooms.add(responseRoom);
        }
        return responseRooms;
    }

    @MessageMapping(URI_PREFIX+"/send-messages")
    public void sendMessage(RequestSendRoomMessage message, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        Message msg = Message.builder().userId(user.getId()).contents(message.getContents()).regDt(message.getSendDt()).build();

        Room room = redisService.findRoom(message.getRoomId());
        room.setLastMsgDt(message.getSendDt());
        room.setLastMsgContents(message.getContents());
        redisService.saveRoom(room);
        redisService.sendMessage(room.getId(), msg);

        for(String userId : room.getInUserIds()) {
            redisService.saveUserInRoom(userId, room.getId());
            webSocketManagerService.sendToUserByUserId("/queue"+URI_PREFIX+"/"+message.getRoomId()+"/message", msg, userId);

            List<Room> rooms = redisService.getUserInRooms(userId);
            List<ResponseRoom> responseRooms = new ArrayList<>();
            for (Room room1 : rooms) {
                Set<User> roomInUsers1 = roomService.getInUsers(room1.getId(), room1.getInUserIds());
                ResponseRoom responseRoom = ResponseRoom.builder()
                        .room(room1)
                        .roomInUsers(roomInUsers1).build();
                responseRooms.add(responseRoom);
            }
            webSocketManagerService.sendToUserByUserId("/queue/rooms", responseRooms, userId);
        }
    }

    @MessageMapping(URI_PREFIX+"/{roomId}/messages")
    @SendToUser("/queue"+URI_PREFIX+"/{roomId}/messages")
    @JsonView({JsonViewApi.class})
    public List<Message> getMessages(@DestinationVariable("roomId") Long roomId,Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        return redisService.getMessages(roomId);
    }

//    @SendToUser("/queue"+URI_PREFIX+"/{roomId}/message")
//    public List<Message> getMessage(@DestinationVariable("roomId") Long roomId,Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
//        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
//        List<RoomMessage> roomMessages = this.roomMessageRepository.findAllByRoomId(roomId);
//        return roomMessages.stream().map(it -> it.getMessage()).collect(Collectors.toList());
//    }

//    @MessageMapping(URI_PREFIX+"/confirm-messages/{id}")
//    public void checkMessage(@DestinationVariable("id") Long id, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
//        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
//        roomMessageRepository.updateChecktByMessageIdAndUserId(true, id, user.getId());
//    }


}
