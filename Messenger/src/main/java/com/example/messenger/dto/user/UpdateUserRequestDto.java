package com.example.messenger.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDto {
    private String name;

    public UpdateUserRequestDto(
            @JsonProperty("name") String name) {
        this.name = name;
    }
}
