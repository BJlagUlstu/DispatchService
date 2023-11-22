package com.example.messenger.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
public class RegistrationRequestDto implements Cloneable {
    private final String email;
    private final String password;
    private final String name;

    public RegistrationRequestDto(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("name") String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public RegistrationRequestDto clone(String password) throws CloneNotSupportedException {
        return new RegistrationRequestDto(
                this.email,
                password,
                name
        );
    }
}
