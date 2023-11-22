package com.example.messenger.controllers;

import com.example.messenger.dto.user.AuthenticationRequestDto;
import com.example.messenger.dto.user.RegistrationRequestDto;
import com.example.messenger.dto.user.UserResponseDto;
import com.example.messenger.exceptions.GenerateJwtTokenException;
import com.example.messenger.exceptions.ReadJwtTokenException;
import com.example.messenger.services.AuthorizationService;
import com.example.messenger.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {
    @Autowired
    private AuthorizationService authenticationService;
    /**
     * @return Return JWT token based on authentication's account data
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthenticationRequestDto requestDto) throws GenerateJwtTokenException, ReadJwtTokenException {
        return authenticationService.login(requestDto);
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registration(@RequestBody RegistrationRequestDto requestDto) throws CloneNotSupportedException {
        return authenticationService.registration(requestDto);
    }

    @GetMapping("/refresh")
    public ResponseEntity<String> refresh(@CookieValue(value = Constants.REFRESH_TOKEN_COOKIE_NAME) String refreshToken) throws GenerateJwtTokenException, ReadJwtTokenException {
        return authenticationService.refresh(refreshToken);
    }
}
