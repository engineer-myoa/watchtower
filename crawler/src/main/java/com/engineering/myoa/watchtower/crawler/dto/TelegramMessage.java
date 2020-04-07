package com.engineering.myoa.watchtower.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TelegramMessage {

    @JsonProperty(value = "chat_id")
    String chatId;
    String text;
}
