package com.example.messenger.services;

import com.example.messenger.dto.user.AuthenticationRequestDto;
import com.example.messenger.dto.user.RegistrationRequestDto;
import com.example.messenger.dto.user.UserResponseDto;
import com.example.messenger.exceptions.GenerateJwtTokenException;
import com.example.messenger.exceptions.ReadJwtTokenException;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthorizationService {
    public ResponseEntity<String> login(AuthenticationRequestDto requestDto) throws ReadJwtTokenException, GenerateJwtTokenException;
    public ResponseEntity<UserResponseDto> registration(RegistrationRequestDto requestDto) throws CloneNotSupportedException;
    public ResponseEntity<String> refresh(String refreshToken) throws ReadJwtTokenException, GenerateJwtTokenException;
}
