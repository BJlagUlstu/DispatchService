package com.example.messenger.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageContentDto {
    @JsonProperty("text")
    private String text;

    public MessageContentDto(String text) {
        this.text = text;
    }
}
