package com.example.messenger.dto.message;

import com.example.messenger.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MessageShortResponseDto {
    @JsonProperty("message_id")
    private UUID messageId;
    @JsonProperty("message_content")
    private MessageContentDto messageContent;
    @JsonProperty("dispatch_time")
    private String dispatchDate;
    @JsonProperty("status")
    private Status status;
    @JsonProperty("sender")
    private SenderResponseDto sender;

    public MessageShortResponseDto() {}

    public MessageShortResponseDto(UUID messageId, MessageContentDto messageContent, String dispatchDate, Status status, SenderResponseDto sender) {
        this.messageId = messageId;
        this.messageContent = messageContent;
        this.dispatchDate = dispatchDate;
        this.status = status;
        this.sender = sender;
    }
}
