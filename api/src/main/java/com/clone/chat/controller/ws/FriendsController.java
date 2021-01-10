package com.clone.chat.controller.ws;

import com.clone.chat.annotation.ModelAttributeMapping;
import com.clone.chat.controller.api.ApiController;
import com.clone.chat.domain.User;
import com.clone.chat.domain.base.UserBase;
import com.clone.chat.model.UserToken;
import com.clone.chat.repository.UserRepository;
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

@Controller("ws-friends-controller")
public class FriendsController {
    public static final String URI_PREFIX = "/friends";

    @Autowired
    private WebSocketManagerService webSocketManagerService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    UserRepository userRepository;


    @MessageMapping(URI_PREFIX)
    @SendToUser("/queue"+URI_PREFIX)
    public List<User> processMessageFromClient(UserBase user, @ModelAttributeMapping UserToken userToken, Principal principal, SimpMessageHeaderAccessor simpMessageHeaderAccessor) throws Exception {
//        String name = new Gson().fromJson(message, Map.class).get("name").toString();
        //messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", name);
//        Optional<User> user = webSocketManagerService.getUser(simpMessageHeaderAccessor);
//        user.orElseThrow(() -> new NoSuchElementException("no Such"));
//        return friendRepository.findByUserId(user.get().getId());
        Optional<User> data = userRepository.findById(userToken.getId());
        return data.get().getFriends();
    }
}
