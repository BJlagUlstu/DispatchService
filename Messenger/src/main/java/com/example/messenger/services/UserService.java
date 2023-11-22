package com.example.messenger.services;

import com.example.messenger.dto.user.UpdateUserRequestDto;
import com.example.messenger.dto.user.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserResponseDto loadUserByLoginIfExist(String login) throws UsernameNotFoundException;
    ResponseEntity<List<UserResponseDto>> loadUsersByQuery(String pattern);
    ResponseEntity<UserResponseDto> updateImage(MultipartFile image, String userEmail);
    void updateActiveStatus(boolean active, String email);
    ResponseEntity<UserResponseDto> updateUserName(UpdateUserRequestDto requestDto, String userEmail);
    ResponseEntity<UserResponseDto> updateUser(UpdateUserRequestDto requestDto, MultipartFile image, String userEmail);
}
