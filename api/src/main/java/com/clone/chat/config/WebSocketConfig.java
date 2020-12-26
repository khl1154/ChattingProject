package com.clone.chat.config;

import com.clone.chat.domain.ChatMessage;
import com.clone.chat.domain.User;
import com.clone.chat.repository.UserRepository;
import com.clone.chat.service.TokenService;
import com.clone.chat.service.WebSocketManagerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Configuration
@Slf4j
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Autowired
    WebSocketManagerService webSocketManagerService;


    @Autowired
    TokenService tokenService;


    @Autowired
    UserRepository userRepository;

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {

                MessageHeaders headers = message.getHeaders();
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS,MultiValueMap.class);
                if (StompCommand.SUBSCRIBE.equals(accessor.getCommand()) || StompCommand.MESSAGE.equals(accessor.getCommand())) {
                    String token = multiValueMap.getFirst(HttpHeaders.AUTHORIZATION);
                    Jws<Claims> claimsJws = tokenService.parserJwt(token);
//                    String sessionId = (String) accessor.getSessionAttributes().get("sessionId");
                    String sessionId = accessor.getSessionId();
                    Optional<User> sessionUser = webSocketManagerService.getUser(sessionId);
                    if (!sessionUser.isPresent()) {
                        Optional<User> dbUser = userRepository.findById(claimsJws.getBody().getSubject());
                        dbUser.orElseThrow(() -> new NoSuchElementException("no source user"));
                        webSocketManagerService.putUser(sessionId, dbUser.get());
                    }
                }

//                StompHeaderAccessor accessor =
//                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
////                    accessor.
////                    Authentication user = ... ; // access authentication header(s)
////                    accessor.setUser(user);
//                }
                return message;
            }
        });
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .addInterceptors(new HttpHandshakeInterceptor(webSocketManagerService))
//                .setHandshakeHandler(new DefaultHandshakeHandler(){
//                    public boolean beforeHandshake(
//                            ServerHttpRequest request,
//                            ServerHttpResponse response,
//                            WebSocketHandler wsHandler,
//                            Map attributes) throws Exception {
//
//                        if (request instanceof ServletServerHttpRequest) {
//                            ServletServerHttpRequest servletRequest
//                                    = (ServletServerHttpRequest) request;
//                            HttpSession session = servletRequest
//                                    .getServletRequest().getSession();
//                            attributes.put("sessionId", session.getId());
//                        }
//                        return true;
//                    }
//                })
                .withSockJS();
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        String sessionId = (String) headerAccessor.getSessionAttributes().get("sessionId");

        webSocketManagerService.removeUser(event.getSessionId());

//        if(username != null) {
//            log.info("User Disconnected : " + username);

//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setType(MessageType.LEAVE);
//            chatMessage.setSender(username);

//            messagingTemplate.convertAndSend("/topic/public", chatMessage);
//        }
    }

}
