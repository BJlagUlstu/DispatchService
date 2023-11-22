package com.example.messenger.domain;

import com.example.messenger.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.messenger.domain.User.TABLE_NAME;

@Entity
@Table(name = TABLE_NAME)
@Getter
@Setter
public class User {
    @Id
    @Column(name = EMAIL_COLUMN_NAME)
    private String email;
    @Column(name = PASSWORD_COLUMN_NAME)
    private String password;
    @Column(name = USER_NAME_COLUMN_NAME)
    private String name;
    @Column(name = ACTIVE_COLUMN_NAME)
    private boolean active;
    @Column(name = IMAGE_PATH_COLUMN_NAME)
    private String imagePath;
    @Column(name = CREATED_AT_COLUMN_NAME)
    private LocalDateTime createdAt;
    @Column(name = LAST_TIME_ONLINE_COLUMN_NAME)
    private LocalDateTime lastTimeOnline;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = USER_ROLE_TABLE_NAME, joinColumns = @JoinColumn(name = USER_ROLE_FOREIGN_KEY_NAME))
    @Enumerated(EnumType.STRING)
    @Column(name = USER_ROLE_ROLE_COLUMN_NAME)
    private List<Role> roles;
    // region constants
    public static final String TABLE_NAME = "chat_user";
    public static final String EMAIL_COLUMN_NAME = "email";
    public static final String PASSWORD_COLUMN_NAME = "password";
    public static final String USER_NAME_COLUMN_NAME = "user_name";
    public static final String ACTIVE_COLUMN_NAME = "active";
    public static final String IMAGE_PATH_COLUMN_NAME = "image_path";
    public static final String CREATED_AT_COLUMN_NAME = "created_at";
    public static final String LAST_TIME_ONLINE_COLUMN_NAME = "last_time_online";
    public static final String USER_ROLE_TABLE_NAME = "user_role";
    public static final String USER_ROLE_FOREIGN_KEY_NAME = "login";
    public static final String USER_ROLE_ROLE_COLUMN_NAME = "role_name";
    // endregion
}
