package com.example.messenger.domain;

import com.example.messenger.domain.help.UserToChatPK;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static com.example.messenger.domain.UserToChat.TABLE_NAME;

@Table(name = TABLE_NAME)
@Entity
@Getter
@Setter
@NamedEntityGraph(
        name = "UserToChatEntityGraph",
        attributeNodes = {
                @NamedAttributeNode(value = "chat", subgraph = "messages"),
                @NamedAttributeNode("user")
        },
        subgraphs = {
                @NamedSubgraph(name = "messages", attributeNodes = {
                        @NamedAttributeNode("messages")
                })
        }
)
public class UserToChat {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CHAT_ID_COLUMN_NAME, referencedColumnName = Chat.CHAT_ID_COLUMN_NAME, updatable = false, insertable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = EMAIL_COLUMN_NAME, referencedColumnName = User.EMAIL_COLUMN_NAME, updatable = false, insertable = false)
    private User user;

    @EmbeddedId
    private UserToChatPK userToChatKey;

    // region constants
    public static final String TABLE_NAME = "user_to_chat";
    public static final String CHAT_ID_COLUMN_NAME = "chat_id";
    public static final String EMAIL_COLUMN_NAME = "email";
    // endregion
}
