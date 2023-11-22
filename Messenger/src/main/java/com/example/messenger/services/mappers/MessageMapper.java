package com.example.messenger.services.mappers;

import com.example.messenger.domain.Message;
import com.example.messenger.dto.message.*;
import com.example.messenger.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final CommonMapper commonMapper;
    private final UserMapper userMapper;

    public Message sendMessageRequestDtoToMessage(SendMessageRequest sendMessageRequest, String senderEmail, LocalDateTime dispatchDate) {
        Message message = new Message();
        message.setSenderEmail(senderEmail);
        message.setChatId(sendMessageRequest.getChatId());
        message.setMessageContent(sendMessageRequest.getMessageContent());
        message.setDispatchDate(dispatchDate);
        message.setStatus(Status.DELIVERED);
        return message;
    }

    public MessageShortResponseDto messageToMessageShortResponseDto(Message message) {
        return new MessageShortResponseDto(
                message.getMessageId(),
                new MessageContentDto(message.getMessageContent()),
                message.getDispatchDate().toString(),
                message.getStatus(),
                userMapper.userToSenderResponseDto(message.getSender())
        );
    }

    public MessageFullResponseDto messageToMessageFullResponseDto(Message message) {
        return new MessageFullResponseDto(
                message.getMessageId(),
                message.getChatId(),
                new MessageContentDto(message.getMessageContent()),
                message.getDispatchDate().toString(),
                message.getStatus(),
                userMapper.userToSenderResponseDto(message.getSender())
        );
    }

    public PageOfMessagesResponseDto messagePageToPageOfMessageResponseDto(Page<Message> messagePage) {
        return new PageOfMessagesResponseDto(
                messagePage
                        .stream()
                        .map(this::messageToMessageShortResponseDto)
                        .toList(),
                messagePage.getTotalPages(),
                messagePage.hasNext()
        );
    }
}
