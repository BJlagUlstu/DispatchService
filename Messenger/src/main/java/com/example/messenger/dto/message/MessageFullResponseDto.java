package com.example.messenger.dto.message;

import com.example.messenger.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class MessageFullResponseDto {
    @JsonProperty("message_id")
    private UUID messageId;
    @JsonProperty("message_content")
    private MessageContentDto messageContent;
    @JsonProperty("dispatch_time")
    private String dispatchDate;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("chat_id")
    private UUID chatId;
    @JsonProperty("sender")
    private SenderResponseDto sender;

    public MessageFullResponseDto() {}

    public MessageFullResponseDto(UUID messageId, UUID chatId, MessageContentDto messageContent, String dispatchDate, Status status, SenderResponseDto sender) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.dispatchDate = dispatchDate;
        this.status = status;
        this.chatId = chatId;
        this.sender = sender;
    }
}
