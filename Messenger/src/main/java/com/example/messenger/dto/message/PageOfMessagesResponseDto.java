package com.example.messenger.dto.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageOfMessagesResponseDto {
    @JsonProperty("messages")
    private List<MessageShortResponseDto> messages;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("has_next_page")
    private boolean hasNextPage;

    public PageOfMessagesResponseDto(List<MessageShortResponseDto> messages, int totalPages, boolean hasNextPage) {
        this.messages = messages;
        this.totalPages = totalPages;
        this.hasNextPage = hasNextPage;
    }
}
