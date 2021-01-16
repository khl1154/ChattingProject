package com.clone.chat.controller.ws.rooms;

import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.ws.rooms.model.CreateRoom;
import com.clone.chat.domain.Room;
import com.clone.chat.domain.User;
import com.clone.chat.domain.UserRoom;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.model.UserToken;
import com.clone.chat.model.error.Error;
import com.clone.chat.repository.RoomRepository;
import com.clone.chat.repository.UserRoomRepository;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.WebSocketManagerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller("ws-rooms-controller")
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
    UserRoomRepository userInChatRoomRepository;


//    @MessageMapping("/fleet/{fleetId}/driver/{driverId}")
    @MessageMapping(URI_PREFIX+"/create-room")
//    @SubscribeMapping("/fleet/{fleetId}/driver/{driverId}")
//    @SendTo("/topic/rooms/{userId}")
    //@DestinationVariable("userId") String userId,
//        Message m,
    public void createRoom(CreateRoom createRoom,  Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        Optional<UserToken> userToken = webSocketManagerService.getUser(simpMessageHeaderAccessor);
        UserToken user = userToken.orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        createRoom.addUser(user.getId());

        Room room = Room.builder().name(createRoom.getName()).build();
        List<String> publishKeys = new ArrayList<>();
        for (String it : CollectionUtils.emptyIfNull(createRoom.getUsers())) {
            room.addUserRoom(UserRoom.builder().userId(it).build());
        }
        room = roomRepository.save(room);

        room.getUserRooms().stream().forEach(it -> {
            List<Room> rooms = userInChatRoomRepository.findAllByUserId(it.getUserId()).stream().map(sit -> sit.getRoom()).collect(Collectors.toList());
            webSocketManagerService.sendToUserByUserId("/queue/rooms", rooms, it.getUserId());

        });
    }

    @MessageMapping(URI_PREFIX)
    @SendToUser("/queue/rooms")
    public List<Room> processMessageFromClient(Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        Optional<UserToken> userToken = webSocketManagerService.getUser(simpMessageHeaderAccessor);
        UserToken user = userToken.orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        return userInChatRoomRepository.findAllByUserId(user.getId()).stream().map(it -> it.getRoom()).collect(Collectors.toList());
    }



}
