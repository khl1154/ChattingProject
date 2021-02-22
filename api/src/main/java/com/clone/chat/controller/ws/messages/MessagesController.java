package com.clone.chat.controller.ws.messages;

import com.clone.chat.code.MsgCode;
import com.clone.chat.controller.ws.messages.model.RequestMessage;
import com.clone.chat.domain.Message;
import com.clone.chat.domain.UserMessage;
import com.clone.chat.exception.BusinessException;
import com.clone.chat.model.UserToken;
import com.clone.chat.model.view.json.JsonViewApi;
import com.clone.chat.repository.UserRepository;
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
import java.util.List;

@Controller("ws-messages-controller")
@RequiredArgsConstructor
public class MessagesController {
    public static final String URI_PREFIX = "/messages";

    private final WebSocketManagerService webSocketManagerService;

    private final SimpMessageSendingOperations messagingTemplate;

    private final UserRepository userRepository;

//
//    @MessageMapping(URI_PREFIX+"/{userId}/send")
//    public void sendDirectMessage(RequestMessage message, @DestinationVariable("userId") String toUserId, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
//        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
//        Message msg = Message.builder().userId(user.getId()).contents(message.getContents()).regDt(message.getSendDt()).build();
//        // 보낸사람
//        UserMessage senderUserMessage = UserMessage.builder()
//                .userId(user.getId())
//                .message(msg)
//                .confirm(true)
//                .build();
//        userMessageRepository.save(senderUserMessage);
//        webSocketManagerService.sendToUserByUserId("/queue/message", senderUserMessage, user.getId());
//
//        // 받는사람
//        UserMessage recipientUserMessage = UserMessage.builder()
//                .userId(toUserId)
//                .message(msg)
//                .confirm(false)
//                .build();
//        userMessageRepository.save(recipientUserMessage);
//        webSocketManagerService.sendToUserByUserId("/queue/message", recipientUserMessage, toUserId);
//
//    }
//
//    @MessageMapping(URI_PREFIX+"/{userId}")
//    @SendToUser("/queue"+URI_PREFIX)
//    @JsonView({JsonViewApi.class})
//    public List<UserMessage> getMessages(@DestinationVariable("userId") String fromUserId, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
//        UserToken user = webSocketManagerService.getUser(simpMessageHeaderAccessor).orElseThrow(() -> new BusinessException(MsgCode.ERROR_AUTH));
//        List<UserMessage> data = userMessageService.getUserMessages(user.getId(), fromUserId);
//        return data;
//    }
}
