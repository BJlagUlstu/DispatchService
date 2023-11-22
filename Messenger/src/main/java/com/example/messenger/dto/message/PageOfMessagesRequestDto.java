package com.example.messenger.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PageOfMessagesRequestDto {
    private UUID chatId;
    private int pageSize;
    private int page;

    public PageOfMessagesRequestDto(
            @JsonProperty("chat_id") UUID chatId,
            @JsonProperty("page_size") int pageSize,
            @JsonProperty("page") int page) {
        this.chatId = chatId;
        this.pageSize = pageSize;
        this.page = page;
    }
}
