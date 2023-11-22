package com.example.messenger.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.messenger.exceptions.GenerateJwtTokenException;
import com.example.messenger.exceptions.ReadJwtTokenException;
import com.example.messenger.utils.TimeProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
@Component
@RequiredArgsConstructor
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.issuer}")
    private String issuer;
    private Algorithm algorithm;
    private final TimeProvider timeProvider;
    @PostConstruct
    private void init() {
        algorithm = Algorithm.HMAC256(secret);
    }
    public String generateToken(Authentication authentication, Duration duration) throws GenerateJwtTokenException {
        List<String> rolesClaim = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Instant startLifeTime = timeProvider.getCurrentTimeOfCurrentTimeZone();
        Instant endLifeTime = timeProvider.getTimeOfCurrentTimeZoneWithOffset(startLifeTime, duration);
        try {
            return JWT
                    .create()
                    .withClaim(ROLES_NAME, rolesClaim)
                    .withSubject(authentication.getName())
                    .withIssuedAt(startLifeTime)
                    .withExpiresAt(endLifeTime)
                    .withIssuer(issuer)
                    .sign(algorithm);
        } catch (IllegalArgumentException exception) {
            throw new GenerateJwtTokenException(exception.getMessage());
        }
    }
    public Authentication getAuthenticationByToken(String token) throws ReadJwtTokenException {
        try {
            DecodedJWT decodedJWT = validateToken(token);

            String subject = decodedJWT.getSubject();
            Collection<SimpleGrantedAuthority> rolesClaim = decodedJWT.getClaim(ROLES_NAME).asList(String.class)
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            return new UsernamePasswordAuthenticationToken(subject, "", rolesClaim);
        } catch (JWTVerificationException exception) {
            throw new ReadJwtTokenException(exception.getMessage());
        }
    }
    private DecodedJWT validateToken(String token) throws JWTVerificationException {
        JWTVerifier jwtVerifier = JWT
                .require(algorithm)
                .withIssuer(issuer)
                .build();
        return jwtVerifier.verify(token);
    }
    // region constants
    public static String ROLES_NAME = "roles";
    // endregion
}
