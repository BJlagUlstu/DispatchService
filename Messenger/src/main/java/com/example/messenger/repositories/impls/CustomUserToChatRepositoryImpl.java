package com.example.messenger.repositories.impls;

import com.example.messenger.domain.*;
import com.example.messenger.repositories.CustomUserToChatRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserToChatRepositoryImpl implements CustomUserToChatRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<UserToChat> findAllByEmail(String email) {
        entityManager.clear();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserToChat> criteriaQuery = criteriaBuilder.createQuery(UserToChat.class);

        Root<UserToChat> userToChatRoot = criteriaQuery.from(UserToChat.class);
        Join<UserToChat, Chat> chatJoin = userToChatRoot.join(UserToChat_.CHAT, JoinType.INNER);
        Join<UserToChat, User> userJoin = userToChatRoot.join(UserToChat_.USER, JoinType.INNER);

        Join<Chat, Message> messageJoin = chatJoin.join(Chat_.MESSAGES, JoinType.INNER);
        messageJoin.on(criteriaBuilder.equal(chatJoin.get(Chat_.LAST_DISPATCH_DATE), messageJoin.get(Message_.DISPATCH_DATE)));

        messageJoin.join(Message_.SENDER, JoinType.INNER);

        criteriaQuery.where(criteriaBuilder.equal(userJoin.get(User_.EMAIL), email));
        TypedQuery<UserToChat> queryResult = entityManager.createQuery(criteriaQuery);
        var result = queryResult.getResultList();
        var messages = result.get(0).getChat().getMessages();
        return queryResult.getResultList();
    }
}
