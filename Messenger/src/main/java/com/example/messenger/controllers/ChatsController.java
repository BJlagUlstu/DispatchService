package com.example.messenger.controllers;

import com.example.messenger.dto.chat.ChatResponseDto;
import com.example.messenger.dto.chat.CreateChatRequestDto;
import com.example.messenger.dto.message.MessageFullResponseDto;
import com.example.messenger.dto.message.PageOfMessagesResponseDto;
import com.example.messenger.dto.message.SendMessageRequest;
import com.example.messenger.services.ChatService;
import com.example.messenger.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatsController {

    private final ChatService chatService;
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public ResponseEntity<List<ChatResponseDto>> getChats(Principal principal) {
        return chatService.loadAllChatsByUser(principal.getName());
    }

    @GetMapping("{chatId}")
    public ResponseEntity<ChatResponseDto> getChat(@PathVariable("chatId") UUID chatId, Principal principal) {
        return chatService.loadChatById(chatId, principal.getName());
    }

    @MessageMapping("/send")
    public void sendMessage(@Payload SendMessageRequest sendMessageRequest, Principal principal) {
        UUID deliveredMessageId = messageService.createNewMessage(sendMessageRequest, principal.getName());

        MessageFullResponseDto deliveredMessage = messageService.loadMessageById(deliveredMessageId);

        chatService.loadEmailsByChat(deliveredMessage.getChatId()).forEach(email -> {
            messagingTemplate.convertAndSendToUser(
                    email, "/messages", deliveredMessage
            );
        });
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> createChat(@RequestBody CreateChatRequestDto createChatRequestDto, Principal principal) {
        return chatService.createChat(createChatRequestDto, principal.getName());
    }

    @GetMapping("/{chat-id}/messages")
    public ResponseEntity<PageOfMessagesResponseDto> getChatMessages(
            @PathVariable("chat-id") UUID chatId,
            @RequestParam("page") int page,
            @RequestParam("page-size") int pageSize
    ) {
        return messageService.loadPageOfMessagesByChat(chatId, page, pageSize);
    }

    @PostMapping("/read-message")
    public void updateMessageStatusToRead(
            @RequestBody List<UUID> messageId
    ) {
        messageService.updateMessageStatusToRead(messageId);
    }
}
