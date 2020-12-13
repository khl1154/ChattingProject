package com.clone.chat.controller.chat;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.clone.chat.controller.user.UserController;
import com.clone.chat.domain.ChatMessage;
import com.clone.chat.dto.Greeting;
import com.clone.chat.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import com.clone.chat.dto.ChatRoomDto;
import com.clone.chat.service.ChatService;
import com.clone.chat.util.ResponseForm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("chat")
public class ChatController {

	Logger logger = LoggerFactory.getLogger(ChatController.class);

	private final ChatService chatService;


	private final UserService userService;


	@PostMapping("/room-invite")
	public ResponseForm invite(@RequestParam List<String> users, Long chatRoomId) {
		chatService.invite(users, chatRoomId);

		return new ResponseForm();
	}
	
	@PostMapping("/room-create")
	public ResponseForm roomCreate(@RequestBody ChatRoomDto dto) {
		Long roomId = chatService.chatRoomCreate(dto);

		return new ResponseForm("roomId", roomId);
	}
	
	@GetMapping("/room-list")
	public ResponseForm roomList(String userId, String search) throws UnsupportedEncodingException {
		List<ChatRoomDto.Response> list = chatService.getList(userId, search);

		return new ResponseForm("list", list);
	}

	//방입장시 알림메시지
	@MessageMapping("/hello/{roomNo}")
	@SendTo("/topic/greetings/{roomNo}")
	public Greeting greeting(ChatMessage message) throws Exception {
		Thread.sleep(100); // delay
		return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}
	//채팅 송신
	@MessageMapping("/chat/{roomNo}")
	@SendTo("/topic/chat/{roomNo}")
	public ChatRoomDto chat(ChatRoomDto chat) throws Exception {

		return new ChatRoomDto(chat.getName(), chat.getMessage());
	}





}
