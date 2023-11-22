package com.example.messenger.repositories;

import com.example.messenger.domain.UserToChat;

import java.util.List;

public interface CustomUserToChatRepository {
    public List<UserToChat> findAllByEmail(String email);
}
