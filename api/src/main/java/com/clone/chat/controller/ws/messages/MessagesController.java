package com.clone.chat.controller.ws.messages;

import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.ws.messages.model.RequestMessage;
import com.clone.chat.controller.ws.rooms.model.RequestCreateRoom;
import com.clone.chat.controller.ws.rooms.model.ResponseRoom;
import com.clone.chat.domain.*;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.model.UserToken;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.repository.RoomIdRepository;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.RedisService;
import com.clone.chat.service.WebSocketManagerService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller("ws-messages-controller")
@RequiredArgsConstructor
public class MessagesController {
    public static final String URI_PREFIX = "/messages";

    private final WebSocketManagerService webSocketManagerService;

    private final SimpMessageSendingOperations messagingTemplate;

    private final UserRepository userRepository;

    private final RoomIdRepository roomIdRepository;

    private final RedisService redisService;


    @MessageMapping(URI_PREFIX+"/{userId}/send")
    public void sendDirectMessage(RequestMessage message, @DestinationVariable("userId") String toUserId, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        Message msg = Message.builder().userId(user.getId()).contents(message.getContents()).regDt(message.getSendDt()).build();
        String[] strings = compareUsers(user.getId(), toUserId);
        String user1 = strings[0];
        String user2 = strings[1];

        Long roomId = redisService.getOneToOneRoom(user1, user2);
        if(roomId == null) roomId = createRoom(user1, user2);

        Room room = redisService.findRoom(roomId);
        room.setLastMsgDt(message.getSendDt());
        room.setLastMsgContents(message.getContents());
        redisService.saveRoom(room);
        redisService.sendMessage(roomId, msg);
        webSocketManagerService.sendToUserByUserId("/queue/message", msg, user1);
        webSocketManagerService.sendToUserByUserId("/queue/message", msg, user2);

    }

    @MessageMapping(URI_PREFIX+"/{userId}")
    @SendToUser("/queue"+URI_PREFIX)
    @JsonView({JsonViewApi.class})
    public List<Message> getMessages(@DestinationVariable("userId") String toUserId, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        String[] strings = compareUsers(user.getId(), toUserId);
        String user1 = strings[0];
        String user2 = strings[1];

        Long roomId = redisService.getOneToOneRoom(user1, user2);
        if(roomId == null) return null;
        return redisService.getMessages(roomId);
    }

    public Long createRoom(String user1, String user2) throws Exception {
        Set<String> inUserIds = new HashSet<>();
        inUserIds.add(user1);
        inUserIds.add(user2);
        Room room = Room.builder().name(user1+":"+user2).inUserIds(inUserIds).build();
        RoomId roomId = roomIdRepository.save(new RoomId());
        room.setId(roomId.getId());
        redisService.saveRoom(room);
        redisService.joinRoom(user1, room);
        redisService.joinRoom(user2, room);
        redisService.createOneToOneRoom(user1, user2, room.getId());
        return room.getId();
    }

    public String[] compareUsers(String user1, String user2) {
        if(user1.compareTo(user2) == 1) {
            String temp = user1;
            user1 = user2;
            user2 = temp;
        }
        return new String[] {user1,user2};
    }
}
