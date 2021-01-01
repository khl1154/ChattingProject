package com.clone.chat.controller.api;

import com.clone.chat.domain.ChatMessage;
import com.clone.chat.domain.UserInChatRoom;
import com.clone.chat.model.ChatRoomDto;
import com.clone.chat.model.Greeting;
import com.clone.chat.model.ResponseForm;
import com.clone.chat.service.ChatService;
import com.clone.chat.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public Map<String, Long> createRoom(@RequestBody ChatRoomDto dto) {
        Long chatRoomId = chatService.createChatRoom(dto);
        Map<String, Long> map = new HashMap<>();
        map.put("chatRoomId",chatRoomId);
        return map;
    }

//    @GetMapping("/rooms")
//    public List<UserInChatRoom> roomList(String userId, String search) {
//        return chatService.getList(userId, search);
//    }


//    //채팅 송신
//    @MessageMapping("/chats/{roomNo}")
//    @SendTo("/topic/chats/{roomNo}")
//    public ChatRoomDto chat(ChatRoomDto chat) throws Exception {
//
//        return new ChatRoomDto(chat.getName(), chat.getMessage());
//    }


}
