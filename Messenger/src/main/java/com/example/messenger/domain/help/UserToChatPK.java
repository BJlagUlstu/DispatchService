package com.example.messenger.domain.help;

import com.example.messenger.domain.UserToChat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
public class UserToChatPK {
    @Column(name = UserToChat.CHAT_ID_COLUMN_NAME)
    private UUID chatId;
    @Column(name = UserToChat.EMAIL_COLUMN_NAME)
    private String email;
}
