package com.clone.chat.controller.ws.rooms;

import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.ws.rooms.model.RequestSendRoomMessage;
import com.clone.chat.controller.ws.rooms.model.RequestCreateRoom;
import com.clone.chat.domain.*;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.model.UserToken;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.domain.Message;
import com.clone.chat.domain.RedisUser;
import com.clone.chat.domain.Room;
import com.clone.chat.domain.RoomMessage;
import com.clone.chat.redisRepository.MessageRepository;
import com.clone.chat.redisRepository.RoomMessageRepository;
import com.clone.chat.redisRepository.RoomRepository;
import com.clone.chat.redisRepository.UserInChatRoomRepository;
import com.clone.chat.repository.*;
import com.clone.chat.service.RoomService;
import com.clone.chat.service.UserService;
import com.clone.chat.service.WebSocketManagerService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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

    private final RoomRepository roomRepository;

    private final MessageRepository messageRepository;

    private final RoomMessageRepository roomMessageRepository;

    private final RoomService roomService;

    private final UserService userService;

    private final RoomIdRepository roomIdRepository;

    private final UserInChatRoomRepository userInChatRoomRepository;

    @MessageMapping(URI_PREFIX+"/create-room")
    public void createRoom(RequestCreateRoom createRoom, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        createRoom.addUser(user.getId());

        Room room = Room.builder().name(createRoom.getName()).build();
        List<String> publishKeys = new ArrayList<>();
        for (String it : CollectionUtils.emptyIfNull(createRoom.getUsers())) {

            User findUser = userService.find(it);
            RedisUser redisUser = userService.getRedisUser(it);
            room.addUser(redisUser);
        }

        RoomId roomId = roomIdRepository.save(new RoomId());
        room.setId(roomId.getId());
        roomRepository.save(room);
        for(String userId : room.getUsers().keySet()) {
            UserInChatRoom userInChatRoom = UserInChatRoom.builder()
                    .roomId(room.getId())
                    .userId(userId).build();
            userInChatRoomRepository.save(userInChatRoom);
            List<Room> rooms = roomService.userRoomFindAllByUserId(userId);
            webSocketManagerService.sendToUserByUserId("/queue/rooms", rooms, userId);
        }
    }

    @MessageMapping(URI_PREFIX)
    @SendToUser("/queue"+URI_PREFIX)
    @JsonView({JsonViewApi.class})
    public List<Room> processMessageFromClient(Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        return roomService.userRoomFindAllByUserId(user.getId());
    }

    @MessageMapping(URI_PREFIX+"/send-messages")
    public void sendMessage(RequestSendRoomMessage message, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        Message msg = Message.builder().userId(user.getId()).contents(message.getContents()).regDt(message.getSendDt()).build();

        Room room = roomRepository.findById(message.getRoomId()).get();
        room.setLastMsgDt(message.getSendDt());
        room.setLastMsgContents(message.getContents());
        roomRepository.save(room);
        RoomMessage roomMessage = RoomMessage.builder()
                .roomId(message.getRoomId())
                .message(msg)
                .confirm(false).build();
        for(String userId : room.getUsers().keySet()) {
            roomMessage.setUserId(userId);
            roomMessageRepository.save(roomMessage);
            webSocketManagerService.sendToUserByUserId("/queue"+URI_PREFIX+"/"+message.getRoomId()+"/message", roomMessage, userId);
            List<Room> rooms = roomService.userRoomFindAllByUserId(userId);
            webSocketManagerService.sendToUserByUserId("/queue/rooms", rooms, userId);
        }
    }

    @MessageMapping(URI_PREFIX+"/{roomId}/messages")
    @SendToUser("/queue"+URI_PREFIX+"/{roomId}/messages")
    @JsonView({JsonViewApi.class})
    public List<RoomMessage> getMessages(@DestinationVariable("roomId") Long roomId,Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        List<RoomMessage> roomMessages = roomService.getRoomMessages(roomId);
        return roomMessages;
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
