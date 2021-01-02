package com.clone.chat.controller.api;

import com.clone.chat.domain.ChatMessage;
import com.clone.chat.domain.ChatRoom;
import com.clone.chat.domain.UserInChatRoom;
import com.clone.chat.model.ChatRoomDto;
import com.clone.chat.model.Greeting;
import com.clone.chat.model.ResponseForm;
import com.clone.chat.service.ChatService;
import com.clone.chat.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping(ChatController.URI_PREFIX)
@Slf4j
@Api(tags = "채팅")
public class ChatController {
    public static final String URI_PREFIX = ApiController.URI_PREFIX + "/chats";

    @Autowired
    TestService testService;

    @Autowired
    ChatService chatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @ApiOperation(value = "방만들기")
    @PostMapping("/rooms")
    public CreateChatRoomResponse createRoom(@RequestBody CreateChatRoomRequest request) {

        ChatRoom chatRoom = ChatRoom.builder()
                .adminId(request.adminId)
                .chatRoomName(request.chatRoomName)
                .build();
        chatService.createChatRoom(chatRoom);
        chatService.invite(request.getInUserIds(),chatRoom.getId());

        return new CreateChatRoomResponse(chatRoom.getId());
    }

//    @GetMapping("/rooms")
//    public List<UserInChatRoom> chatRoomList(@RequestBody String userId ) {
//        return chatService.getList(userId, search);
//    }

    //채팅 송신
    @MessageMapping("/chats/{roomNo}")
    @SendTo("/topic/chats/{roomNo}")
    public ChatRoomDto chat(ChatRoomDto chat) throws Exception {

        return new ChatRoomDto(chat.getName(), chat.getMessage());
    }

    @Data
    @NoArgsConstructor
    static class CreateChatRoomRequest {
        private String adminId;
        private String chatRoomName;
        private List<String> inUserIds;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class CreateChatRoomResponse {
        private Long chatRoomId;
    }


}
