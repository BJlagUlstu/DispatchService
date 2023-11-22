package com.example.messenger.services.impls;

import com.example.messenger.domain.Chat;
import com.example.messenger.domain.Message;
import com.example.messenger.domain.UserToChat;
import com.example.messenger.domain.help.UserToChatPK;
import com.example.messenger.dto.chat.ChatResponseDto;
import com.example.messenger.dto.chat.CreateChatRequestDto;
import com.example.messenger.dto.message.MessageFullResponseDto;
import com.example.messenger.dto.message.PageOfMessagesRequestDto;
import com.example.messenger.dto.message.PageOfMessagesResponseDto;
import com.example.messenger.dto.message.SendMessageRequest;
import com.example.messenger.repositories.ChatRepository;
import com.example.messenger.repositories.MessageRepository;
import com.example.messenger.repositories.CustomUserToChatRepository;
import com.example.messenger.repositories.UserToChatRepository;
import com.example.messenger.services.ChatService;
import com.example.messenger.services.mappers.ChatMapper;
import com.example.messenger.services.mappers.CommonMapper;
import com.example.messenger.services.mappers.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserToChatRepository userToChatRepository;
    private final CommonMapper commonMapper;
    private final ChatMapper chatMapper;

    @Override
    @Transactional
    public ResponseEntity<List<ChatResponseDto>> loadAllChatsByUser(String userEmail) {
        List<UserToChat> result = userToChatRepository.findAllByEmail(userEmail);
        return ResponseEntity.ok(result
                .stream()
                .map(userToChat -> chatMapper.mapChatToChatResponseDto(
                                userToChat.getChat(),
                                userToChatRepository.findByChatId(userToChat.getChat().getChatId())
                                        .stream()
                                        .map(userToChatP -> userToChatP.getUserToChatKey().getEmail())
                                        .toList(),
                                userEmail
                        )
                )
                .toList()
        );
    }

    @Override
    public ResponseEntity<ChatResponseDto> loadChatById(UUID chatId, String userEmail) {
        Chat findedChat = chatRepository.findById(chatId).orElseThrow();
        return ResponseEntity.ok(chatMapper.mapChatToChatResponseDto(
                findedChat,
                userToChatRepository.findByChatId(findedChat.getChatId())
                        .stream()
                        .map(userToChatP -> userToChatP.getUserToChatKey().getEmail())
                        .toList(),
                userEmail
        ));
    }

    @Override
    public List<String> loadEmailsByChat(UUID chatId) {
        return userToChatRepository.findByChatId(chatId)
                .stream()
                .map(userToChat -> userToChat.getUserToChatKey().getEmail())
                .toList();
    }

    @Override
    @Transactional
    public ResponseEntity<UUID> createChat(CreateChatRequestDto createChatRequestDto, String creatorEmail) {
        Chat chat = chatRepository.save(new Chat());

        List<String> dtoMembers = createChatRequestDto.getParticipants();
        dtoMembers.add(creatorEmail);

        List<UserToChat> chatMembers = new ArrayList<>();
        createChatRequestDto.getParticipants().forEach((member) -> {
            UserToChat userToChat = new UserToChat();
            UserToChatPK userToChatPK = new UserToChatPK();
            userToChatPK.setEmail(member);
            userToChatPK.setChatId(chat.getChatId());
            userToChat.setUserToChatKey(userToChatPK);
            chatMembers.add(userToChat);
        });

        userToChatRepository.saveAllAndFlush(chatMembers);
        return ResponseEntity.ok(chat.getChatId());
    }
}
