package com.example.messenger.security;

import com.example.messenger.exceptions.ReadJwtTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class JwtChanelInterceptor implements ChannelInterceptor {
    @Autowired private JwtProvider jwtProvider;
    @Value("${jwt.header}")
    String jwtHeader;
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = ((List<?>)((Map<?, ?>) message.getHeaders().get("nativeHeaders")).get(jwtHeader)).get(0).toString();
            try {
                if (token != null) {
                    Authentication authentication = jwtProvider.getAuthenticationByToken(token);
                    accessor.setUser(authentication);
                }
            } catch (ReadJwtTokenException exception) {
                exception.printStackTrace();
            }
        }
        return message;
    }
}
