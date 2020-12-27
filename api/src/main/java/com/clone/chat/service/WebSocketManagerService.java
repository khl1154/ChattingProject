package com.clone.chat.service;

import com.clone.chat.domain.User;
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

    public Map<String, User> users = new HashMap<>();

    public void putUser(String sessionId, User user){
        this.users.put(sessionId, user);
    }
    public Optional<User> getUser(String sessionId){
        return Optional.ofNullable(this.users.get(sessionId));
    }

    public Optional<User> findByUserId(String id){
        return users.values().stream().filter(it -> null != it && id.equals(it.getId())).findAny();
    }
    public Optional<Map.Entry<String, User>> findEntreSetByUserId(String id){
        return users.entrySet().stream().filter(it -> null != it.getValue() && id.equals(it.getValue().getId())).findAny();
    }

    public User removeUser(String sessionId) {
        return users.remove(sessionId);
    }
    public void removeByUserId(String id) {
        Optional<Map.Entry<String, User>> entreSetByUserId = findEntreSetByUserId(id);
        if(entreSetByUserId.isPresent()){
            this.removeUser(entreSetByUserId.get().getKey());
        }
    }

    public Optional<User> getUser(SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
        String sessionId = (String)simpMessageHeaderAccessor.getSessionAttributes().get("sessionId");
        return this.getUser(sessionId);
    }
}
