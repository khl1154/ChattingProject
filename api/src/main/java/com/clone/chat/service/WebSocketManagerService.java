package com.clone.chat.service;

import com.clone.chat.domain.User;
import com.clone.chat.model.UserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WebSocketManagerService {

    @Autowired
    SimpMessagingTemplate template;

    public static Map<String, UserToken> users = new HashMap<>();

    public void putUser(String sessionId, UserToken user){
        this.users.put(sessionId, user);
    }
    public Optional<UserToken> getUser(String sessionId){
        return Optional.ofNullable(this.users.get(sessionId));
    }

    public Optional<UserToken> findByUserId(String id){
        return users.values().stream().filter(it -> null != it && id.equals(it.getId())).findAny();
    }
    public Optional<Map.Entry<String, UserToken>> findEntreSetByUserId(String id){
        return users.entrySet().stream().filter(it -> null != it.getValue() && id.equals(it.getValue().getId())).findAny();
    }

    public UserToken removeUser(String sessionId) {
        return users.remove(sessionId);
    }
    public void removeByUserId(String id) {
        Optional<Map.Entry<String, UserToken>> entreSetByUserId = findEntreSetByUserId(id);
        if(entreSetByUserId.isPresent()){
            this.removeUser(entreSetByUserId.get().getKey());
        }
    }

    public Optional<UserToken> getUser(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        String sessionId = (String)simpMessageHeaderAccessor.getSessionAttributes().get("sessionId");
        Optional<UserToken> user = this.getUser(sessionId);
        if(user.isPresent()) {
            return user;
        } else {
            return this.getUser((String)simpMessageHeaderAccessor.getHeader("simpSessionId"));
        }
    }


    public void send(String destination, Object data) {

            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setLeaveMutable(true);
            template.convertAndSend(destination, data, headerAccessor.getMessageHeaders());
    }


    public void sendToUserByUserId(String destination, Object data, List<String> userIds) {
        sendToUserByUserId(destination, data, userIds.stream().toArray(String[]::new));
    }
    public void sendToUserByUserId(String destination, Object data, String ... userIds) {
        for (String userId : userIds) {
            if (findEntreSetByUserId(userId).isPresent()) {
                this.sendToUser(destination, data, findEntreSetByUserId(userId).get().getKey());
            }
        }
    }

    public void sendToUser(String destination, Object data, String ... sessionIds) {

        //"/queue/friends"
//        Optional<Map.Entry<String, User>> user1 = webSocketManagerService.findEntreSetByUserId("user1");
//        if (user1.isPresent() && null != user1.get().getValue()) {
        for (String sessionId : sessionIds) {
            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            headerAccessor.setSessionId(sessionId);
            headerAccessor.setLeaveMutable(true);
            template.convertAndSendToUser(sessionId, destination, data, headerAccessor.getMessageHeaders());
        }

        //
//        }

//        for (String listener : listeners) {
//            log.info("Sending notification to " + listener);
//            SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
//            headerAccessor.setSessionId(listener);
//            headerAccessor.setLeaveMutable(true);
//            int value = (int) Math.round(Math.random() * 100d);
//            template.convertAndSendToUser(
//                    listener,
//                    "/notification/item",
//                    new Notification(Integer.toString(value)),
//                    headerAccessor.getMessageHeaders());
//        }
    }
}
