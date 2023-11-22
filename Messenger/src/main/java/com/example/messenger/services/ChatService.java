package com.example.messenger.services;

import com.example.messenger.dto.chat.ChatResponseDto;
import com.example.messenger.dto.chat.CreateChatRequestDto;
import com.example.messenger.dto.message.MessageFullResponseDto;
import com.example.messenger.dto.message.PageOfMessagesRequestDto;
import com.example.messenger.dto.message.PageOfMessagesResponseDto;
import com.example.messenger.dto.message.SendMessageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    public ResponseEntity<List<ChatResponseDto>> loadAllChatsByUser(String userEmail);
    public ResponseEntity<ChatResponseDto> loadChatById(UUID chatId, String userEmail);
    public List<String> loadEmailsByChat(UUID chatId);
    public ResponseEntity<UUID> createChat(CreateChatRequestDto createChatRequestDto, String creatorEmail);
}
