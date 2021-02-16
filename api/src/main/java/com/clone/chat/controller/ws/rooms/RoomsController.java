package com.clone.chat.controller.ws.rooms;

import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.ws.rooms.model.RequestSendRoomMessage;
import com.clone.chat.controller.ws.rooms.model.RequestCreateRoom;
import com.clone.chat.domain.*;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.model.UserToken;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.redisRepository.MessageRepository;
import com.clone.chat.redisRepository.RoomMessageRepository;
import com.clone.chat.redisRepository.RoomRepository;
import com.clone.chat.redisRepository.UserRoomRepository;
import com.clone.chat.repository.*;
import com.clone.chat.service.RoomService;
import com.clone.chat.service.WebSocketManagerService;
import com.fasterxml.jackson.annotation.JsonView;
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
import java.util.stream.Collectors;

@Controller("ws-rooms-controller")
@Slf4j
public class RoomsController {
    public static final String URI_PREFIX = "/rooms";

    @Autowired
    private WebSocketManagerService webSocketManagerService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRoomRepository userRoomRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RoomMessageRepository roomMessageRepository;

    @Autowired
    RoomService roomService;




    @MessageMapping(URI_PREFIX+"/create-room")
    public void createRoom(RequestCreateRoom createRoom, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        createRoom.addUser(user.getId());

        Room room = Room.builder().name(createRoom.getName()).build();
        List<String> publishKeys = new ArrayList<>();
        for (String it : CollectionUtils.emptyIfNull(createRoom.getUsers())) {
            User findUser = userRepository.findById(it).get();
            RedisUser redisUser = RedisUser.builder()
                    .id(findUser.getId())
                    .nickName(findUser.getNickName())
                    .statusMsg(findUser.getStatusMsg())
                    .build();
            if(findUser.getFile() != null)
                redisUser.setFilePath(findUser.getFile().getFilePath());
            room.addUser(redisUser);
        }
//        for (String it : CollectionUtils.emptyIfNull(createRoom.getUsers())) {
//            UserRoom userRoom = UserRoom.builder().userId(it).roomId(room.getId()).build();
//            userRoomRepository.save(userRoom);
//            room.addUserRoom(userRoom);
//        }
        roomRepository.save(room);
        for(String userId : room.getUsers().keySet()) {
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
        Message msg = Message.builder().userId(user.getId()).contents(message.getContents()).build();

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
//        webSocketManagerService.sendToUserByUserId("/queue"+URI_PREFIX+"/"+message.getRoomId()+"/message", recipientRoomMessage, user.getId());
//
//        userRoomRepository.findAllByRoom(message.getRoomId()).stream().forEach(it -> {
//            msg.addRoomMessage(RoomMessage.builder().roomId(message.getRoomId()).userId(it.getUserId()).confirm(false).build());
//        });
//        Message saveMsg = messageRepository.save(msg);
//
//        Room room = roomRepository.findById(message.getRoomId()).get();
//        room.setLastMsgContents(message.getContents());
//        room.setLastMsgDt(message.getSendDt());
//        roomRepository.save(room);
//
//        room.getUserRooms().forEach(it -> {
//            webSocketManagerService.sendToUserByUserId("/queue"+URI_PREFIX+"/"+message.getRoomId()+"/message", saveMsg, it.getUserId());
//            List<Room> rooms = roomService.userRoomFindAllByUserId(it.getUserId());
//            webSocketManagerService.sendToUserByUserId("/queue/rooms", rooms, it.getUserId());
//        });
    }

    @MessageMapping(URI_PREFIX+"/{roomId}/messages")
    @SendToUser("/queue"+URI_PREFIX+"/{roomId}/messages")
    @JsonView({JsonViewApi.class})
    public List<RoomMessage> getMessages(@DestinationVariable("roomId") String roomId,Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
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

    @MessageMapping(URI_PREFIX+"/confirm-messages/{id}")
    public void checkMessage(@DestinationVariable("id") Long id, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        roomMessageRepository.updateChecktByMessageIdAndUserId(true, id, user.getId());
    }


}
