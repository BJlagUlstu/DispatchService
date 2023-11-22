package com.example.messenger.controllers;

import com.example.messenger.dto.user.UpdateUserRequestDto;
import com.example.messenger.dto.user.UserResponseDto;
import com.example.messenger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> loadUsersByQuery(
            @RequestParam(value = "q", defaultValue = "") String query
    ) {
        return userService.loadUsersByQuery(query);
    }

    @PostMapping("/update-image")
    public ResponseEntity<UserResponseDto> updateImage(
            @RequestPart("image") MultipartFile image,
            Principal principal
    ) {
        return userService.updateImage(image, principal.getName());
    }

    @PostMapping("/update-status")
    public void updateStatus(
            @RequestBody boolean isOnline,
            Principal principal
    ) {
        userService.updateActiveStatus(isOnline, principal.getName());
    }

    @PostMapping("/update")
    public ResponseEntity<UserResponseDto> updateUser(
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "name", required = false) UpdateUserRequestDto requestDto,
            Principal principal
    ) {
        return userService.updateUser(requestDto, image, principal.getName());
    }

    @PostMapping("/update-name")
    public ResponseEntity<UserResponseDto> updateName(
            @RequestBody UpdateUserRequestDto requestDto,
            Principal principal
    ) {
        return userService.updateUserName(requestDto, principal.getName());
    }
}
