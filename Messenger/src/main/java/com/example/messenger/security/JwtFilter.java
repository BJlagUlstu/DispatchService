package com.example.messenger.security;

import com.example.messenger.exceptions.ReadJwtTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();
    @Value("${jwt.header}")
    String jwtHeader;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(jwtHeader);
        try {
            if (token != null) {
                logger.trace("JWT token detected, authentication begins");
                Authentication authentication = jwtProvider.getAuthenticationByToken(token);
                logger.trace("The JWT token has been validated, the process of writing authentication to the security context begins");
                SecurityContext securityContext = securityContextHolderStrategy.createEmptyContext();
                securityContext.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(securityContext);
                logger.trace("Authentication with JWT token was over");
            }
        } catch (ReadJwtTokenException exception) {
            logger.info(String.format("The Jwt token was not valid. Validation Error Message: %s", exception.getMessage()));
        }
        filterChain.doFilter(request, response);
    }
}
