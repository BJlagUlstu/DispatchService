package com.example.messenger.repositories;

import com.example.messenger.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    public Optional<User> findByEmail(String email);

    public List<User> findByNameLikeIgnoreCaseOrderByName(String name);

    @Query("update User u set u.active = :active, u.lastTimeOnline = :lastTimeOnline" +
            " where u.email = :email")
    @Modifying
    public void updateActive(
            @Param("email") String email,
            @Param("active") boolean active,
            @Param("lastTimeOnline") LocalDateTime lastTimeOnline
    );

    @Query("update User u set u.name = :name" +
            " where u.email = :email")
    @Modifying
    public User updateUser(
            @Param("name") String name,
            @Param("email") String email
    );
}
