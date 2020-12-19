package com.clone.chat.controller.api;

import com.clone.chat.controller.SwaggerController;
import com.clone.chat.domain.ChatMessage;
import com.clone.chat.domain.UserInChatRoom;
import com.clone.chat.model.*;
import com.clone.chat.service.ChatService;
import com.clone.chat.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(ChatController.URI_PREFIX)
@Slf4j
@Api(tags = "체팅")
public class ChatController {
    public static final String URI_PREFIX = ApiController.URI_PREFIX+"/chats";

    private final ChatService chatService;


    private final UserService userService;



    @ApiOperation(value = "방만들기")
    @PostMapping("/rooms")
    public ResponseForm roomCreate(@RequestBody ChatRoomDto dto) {
        Long roomId = chatService.chatRoomCreate(dto);

        return new ResponseForm("roomId", roomId);
    }

    @GetMapping("/rooms")
    public List<UserInChatRoom> roomList(String userId, String search) {
        return chatService.getList(userId, search);
    }

    //방입장시 알림메시지
    @MessageMapping("/joins/{roomNo}")
    @SendTo("/topics/greetings/{roomNo}")
    public Greeting greeting(ChatMessage message) throws Exception {
        Thread.sleep(100); // delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    //채팅 송신
    @MessageMapping("/chats/{roomNo}")
    @SendTo("/topic/chats/{roomNo}")
    public ChatRoomDto chat(ChatRoomDto chat) throws Exception {

        return new ChatRoomDto(chat.getName(), chat.getMessage());
    }


}
