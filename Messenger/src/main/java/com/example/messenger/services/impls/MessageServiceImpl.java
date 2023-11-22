package com.example.messenger.services.impls;

import com.example.messenger.domain.Chat;
import com.example.messenger.domain.Message;
import com.example.messenger.dto.message.MessageFullResponseDto;
import com.example.messenger.dto.message.PageOfMessagesResponseDto;
import com.example.messenger.dto.message.SendMessageRequest;
import com.example.messenger.enums.Status;
import com.example.messenger.repositories.ChatRepository;
import com.example.messenger.repositories.MessageRepository;
import com.example.messenger.services.MessageService;
import com.example.messenger.services.mappers.CommonMapper;
import com.example.messenger.services.mappers.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final CommonMapper commonMapper;
    @Override
    @Transactional
    public UUID createNewMessage(SendMessageRequest sendMessageRequest, String senderEmail) {
        Chat chat = chatRepository.findById(sendMessageRequest.getChatId()).orElseThrow();

        LocalDateTime dispatchTime = LocalDateTime.now();
        Message sendedMessage = messageRepository.saveAndFlush(messageMapper.sendMessageRequestDtoToMessage(sendMessageRequest, senderEmail, dispatchTime));

        chat.setLastDispatchDate(dispatchTime);
        chatRepository.flush();
        return sendedMessage.getMessageId();
    }

    @Override
    public ResponseEntity<PageOfMessagesResponseDto> loadPageOfMessagesByChat(UUID chatId, int page, int pageSize) {
        Pageable pageable = Pageable.ofSize(pageSize).withPage(page);
        Page<Message> pageOfMessage = messageRepository.findByChatIdOrderByDispatchDateDesc(chatId, pageable);
        return ResponseEntity.ok(messageMapper.messagePageToPageOfMessageResponseDto(pageOfMessage));
    }

    @Override
    @Transactional
    public void updateMessageStatusToRead(List<UUID> messagesIds) {
        messageRepository.updateMessagesStatus(Status.READ, messagesIds);
    }

    @Override
    @Transactional
    public MessageFullResponseDto loadMessageById(UUID messageId) {
        Message message = messageRepository.findById(messageId).orElseThrow();
        var x  = message.getSender().getImagePath();
        return messageMapper.messageToMessageFullResponseDto(message);
    }
}
