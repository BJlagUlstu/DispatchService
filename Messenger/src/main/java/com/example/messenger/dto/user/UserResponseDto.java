package com.example.messenger.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class UserResponseDto {
    private String email;
    private String name;
    private boolean isOnline;
    private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime lastTimeOnline;

    private UserResponseDto() {}

    public UserResponseDto(
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("is_online") boolean isOnline,
            @JsonProperty("image_path") String imagePath,
            @JsonProperty("created_at") LocalDateTime createdAt,
            @JsonProperty("last_time_online") LocalDateTime lastTimeOnline) {
        this.email = email;
        this.name = name;
        this.isOnline = isOnline;
        this.imagePath = imagePath;
        this.createdAt = createdAt;
        this.lastTimeOnline = lastTimeOnline;
    }
}
