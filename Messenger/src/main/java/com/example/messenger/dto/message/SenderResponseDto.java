package com.example.messenger.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SenderResponseDto {
    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;
    @JsonProperty("image_path")
    private String imagePath;

    public SenderResponseDto() {}

    public SenderResponseDto(
            String email,
            String name,
            String imagePath) {
        this.email = email;
        this.name = name;
        this.imagePath = imagePath;
    }
}
