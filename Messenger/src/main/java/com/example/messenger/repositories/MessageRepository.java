package com.example.messenger.repositories;

import com.example.messenger.domain.Message;
import com.example.messenger.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    public Page<Message> findByChatIdOrderByDispatchDateDesc(UUID chatId, Pageable pageable);

    @Query("update Message m set m.status = :status" +
            " where m.messageId in :messagesIds")
    @Modifying
    public void updateMessagesStatus(
            @Param("status") Status status,
            @Param("messagesIds") List<UUID> messagesIds
    );
}
