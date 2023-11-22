package com.example.messenger.dto.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateChatRequestDto {
    private List<String> participants;

    public CreateChatRequestDto(
            @JsonProperty("participants") List<String> participants
    ) {
        this.participants = participants;
    }
}
