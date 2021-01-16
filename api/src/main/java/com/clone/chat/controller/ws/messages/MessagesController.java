package com.clone.chat.controller.ws.messages;

import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.ws.messages.model.ToRoomMessage;
import com.clone.chat.controller.ws.messages.model.ToUserMessage;
import com.clone.chat.domain.Message;
import com.clone.chat.domain.RoomMessage;
import com.clone.chat.domain.UserMessage;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.model.UserToken;
import com.clone.chat.repository.MessageRepository;
import com.clone.chat.repository.UserMessageRepository;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.repository.UserRoomRepository;
import com.clone.chat.service.WebSocketManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Optional;

@Controller("ws-messages-controller")
public class MessagesController {
    public static final String URI_PREFIX = "/messages";

    @Autowired
    private WebSocketManagerService webSocketManagerService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoomRepository userRoomRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserMessageRepository userMessageRepository;

    @MessageMapping(URI_PREFIX+"/send-user-messages")
    public void createRoom(ToUserMessage message, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        Optional<UserToken> userToken = webSocketManagerService.getUser(simpMessageHeaderAccessor);
        UserToken user = userToken.orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        Message msg = Message.builder().userId(user.getId()).contents(message.getContents()).build();
        msg.addUserMessage(UserMessage.builder().userId(message.getUserId()).build());
        messageRepository.save(msg);
    }

    @MessageMapping(URI_PREFIX+"/send-room-messages")
    public void createRoom(ToRoomMessage message, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
        Optional<UserToken> userToken = webSocketManagerService.getUser(simpMessageHeaderAccessor);
        UserToken user = userToken.orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
        Message msg = Message.builder().userId(user.getId()).contents(message.getContents()).build();
        userRoomRepository.findAllByRoom(message.getRoomId()).stream().forEach(it -> {
            msg.addRoomMessage(RoomMessage.builder().roomId(message.getRoomId()).userId(it.getUserId()).build());
        });
        messageRepository.save(msg);
    }

}
