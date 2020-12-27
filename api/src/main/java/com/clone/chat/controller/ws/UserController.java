package com.clone.chat.controller.ws;

import com.clone.chat.domain.Friend;
import com.clone.chat.domain.User;
import com.clone.chat.repository.FriendRepository;
import com.clone.chat.service.WebSocketManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller("WebScoket_UserController")
public class UserController {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private WebSocketManagerService webSocketManagerService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;



    @MessageMapping("/friends")
    @SendToUser("/queue/friends")
    public List<Friend> processMessageFromClient(Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
//        String name = new Gson().fromJson(message, Map.class).get("name").toString();
        //messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", name);
        Optional<User> user = webSocketManagerService.getUser(simpMessageHeaderAccessor);
        user.orElseThrow(() -> new NoSuchElementException("no Such"));
        return friendRepository.findByUserId(user.get().getId());
    }


    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

}
