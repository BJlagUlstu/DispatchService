package com.example.messenger.services.mappers;

import com.example.messenger.domain.Chat;
import com.example.messenger.domain.Message;
import com.example.messenger.dto.chat.ChatResponseDto;
import com.example.messenger.dto.message.MessageShortResponseDto;
import com.example.messenger.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final CommonMapper commonMapper;
    private final MessageMapper messageMapper;

    public ChatResponseDto mapChatToChatResponseDto(Chat chat, List<String> participantsEmails, String userEmail) {
        List<Message> messageList = chat.getMessages();
        Message lastMessage = messageList.get(messageList.size() - 1);
        return new ChatResponseDto(
                chat.getChatId(),
                messageMapper.messageToMessageShortResponseDto(lastMessage),
                participantsEmails,
                !lastMessage.getSender().getEmail().equals(userEmail) && lastMessage.getStatus().equals(Status.READ)
        );
    }
}
