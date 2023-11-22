package com.example.messenger.repositories;

import com.example.messenger.domain.UserToChat;
import com.example.messenger.domain.help.UserToChatPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserToChatRepository extends JpaRepository<UserToChat, UserToChatPK>, CustomUserToChatRepository {
    @Query("select utc from UserToChat utc where utc.userToChatKey.chatId = :chatId")
    public List<UserToChat> findByChatId(@Param("chatId") UUID chatId);
}
