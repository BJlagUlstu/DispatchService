package com.example.messenger.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.data.jpa.repository.EntityGraph;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.example.messenger.domain.Chat.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Getter
@Setter
@NamedEntityGraph(
        name = "ChatEntityGraph",
        attributeNodes = {
                @NamedAttributeNode("messages")
        }
)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = CHAT_ID_COLUMN_NAME)
    private UUID chatId;
    @Column(name = LAST_DISPATCH_DATE_COLUMN_NAME)
    private LocalDateTime lastDispatchDate;
    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<Message> messages;

    // region constants
    public static final String TABLE_NAME = "chat";
    public static final String CHAT_ID_COLUMN_NAME = "chat_id";
    public static final String LAST_DISPATCH_DATE_COLUMN_NAME = "last_dispatch_date";

    // endregion
}
