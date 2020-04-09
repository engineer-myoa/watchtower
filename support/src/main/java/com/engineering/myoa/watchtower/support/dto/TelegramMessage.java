package com.engineering.myoa.watchtower.support.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * TelegramMessage
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramMessage {

    @JsonProperty(value = "chat_id")
    String chatId;
    String text;
    @JsonProperty(value = "disable_web_page_preview")
    Boolean disableWebPagePreview;

    public static TelegramMessage of(String chatId, String text) {
        return new TelegramMessage(chatId, text, true);
    }
}
