package com.example.messenger.services;

import com.example.messenger.dto.message.MessageFullResponseDto;
import com.example.messenger.dto.message.PageOfMessagesResponseDto;
import com.example.messenger.dto.message.SendMessageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    public UUID createNewMessage(SendMessageRequest sendMessageRequest, String senderEmail);
    public ResponseEntity<PageOfMessagesResponseDto> loadPageOfMessagesByChat(UUID chatId, int page, int pageSize);
    public void updateMessageStatusToRead(List<UUID> messagesIds);
    public MessageFullResponseDto loadMessageById(UUID messageId);
}
