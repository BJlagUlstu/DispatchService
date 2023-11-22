package com.example.messenger.domain;

import com.example.messenger.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.example.messenger.domain.Message.TABLE_NAME;

@Table(name = TABLE_NAME)
@Entity
@Getter
@Setter
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = MESSAGE_ID_COLUMN_NAME)
    private UUID messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CHAT_ID_COLUMN_NAME, referencedColumnName = Chat.CHAT_ID_COLUMN_NAME, updatable = false, insertable = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SENDER_EMAIL_COLUMN_NAME, referencedColumnName = User.EMAIL_COLUMN_NAME, updatable = false, insertable = false)
    private User sender;

    @Column(name = CHAT_ID_COLUMN_NAME)
    private UUID chatId;

    @Column(name = SENDER_EMAIL_COLUMN_NAME)
    private String senderEmail;

    @Column(name = MESSAGE_CONTENT_COLUMN_NAME)
    private String messageContent;

    @Column(name = DISPATCH_DATE_COLUMN_NAME)
    private LocalDateTime dispatchDate;

    @Column(name = STATUS_COLUMN_NAME)
    @Enumerated(EnumType.STRING)
    private Status status;

    // region constants
    public static final String TABLE_NAME = "chat_message";
    public static final String MESSAGE_ID_COLUMN_NAME = "message_id";
    public static final String CHAT_ID_COLUMN_NAME = "chat_id";
    public static final String SENDER_EMAIL_COLUMN_NAME = "sender_email";
    public static final String MESSAGE_CONTENT_COLUMN_NAME = "message_content";
    public static final String DISPATCH_DATE_COLUMN_NAME = "dispatch_date";
    public static final String STATUS_COLUMN_NAME = "status";
    // endregion
}
