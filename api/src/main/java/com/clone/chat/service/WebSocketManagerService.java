package com.clone.chat.service;

import com.clone.chat.domain.User;
import com.clone.chat.model.UserToken;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class WebSocketManagerService {

    public Map<String, UserToken> users = new HashMap<>();

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
}
