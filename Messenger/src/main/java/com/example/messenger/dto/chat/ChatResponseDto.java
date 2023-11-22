package com.example.messenger.dto.chat;

import com.example.messenger.dto.message.MessageShortResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ChatResponseDto {
    @JsonProperty("chat_id")
    private UUID chatId;
    @JsonProperty("last_message")
    private MessageShortResponseDto lastMessage;
    @JsonProperty("participants")
    private List<String> participants;
    @JsonProperty("has_unread_messages")
    private boolean hasUnreadMessages;
    public ChatResponseDto() {}
    public ChatResponseDto(UUID chatId, MessageShortResponseDto lastMessage, List<String> participants, boolean hasUnreadMessages) {
        this.chatId = chatId;
        this.lastMessage = lastMessage;
        this.participants = participants;
        this.hasUnreadMessages = hasUnreadMessages;
    }
}
