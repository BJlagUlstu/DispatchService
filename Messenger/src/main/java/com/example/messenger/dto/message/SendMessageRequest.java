package com.example.messenger.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SendMessageRequest {
    private UUID chatId;
    private String messageContent;

    public SendMessageRequest(
            @JsonProperty("chat_id") UUID chatId,
            @JsonProperty("message_content") String messageContent) {
        this.chatId = chatId;
        this.messageContent = messageContent;
    }
}
