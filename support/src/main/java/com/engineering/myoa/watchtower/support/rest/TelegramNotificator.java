package com.engineering.myoa.watchtower.support.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.engineering.myoa.watchtower.support.dto.TelegramMessage;

import lombok.extern.slf4j.Slf4j;

/**
 * TelegramNotificator
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Slf4j
@Component
public class TelegramNotificator {

    private static final String SEND_METHOD = "sendMessage";

    private final RestTemplate restTemplate;
    private final String telegramBotApi;
    private final HttpHeaders defaultHeaders;

    public TelegramNotificator(RestTemplate restTemplate,
                               @Value("${telegram-bot-api}") String telegramBotApi) {
        this.restTemplate = restTemplate;
        this.telegramBotApi = telegramBotApi;
        defaultHeaders = getDefaultHeaders();
    }

    public void notifyMessage(String botToken, String chatId, String message) {

        restTemplate.exchange(getUriString(botToken),
                              HttpMethod.POST,
                              getRequestEntity(chatId, message),
                              Map.class);
    }

    private String getUriString(String botToken) {
        return UriComponentsBuilder
                .fromUriString(String.format("%s/%s/%", telegramBotApi, botToken, SEND_METHOD))
                .build()
                .toUriString();
    }

    private HttpEntity<TelegramMessage> getRequestEntity(String chatId, String message) {
        return new HttpEntity<>(TelegramMessage.of(chatId, message), defaultHeaders);
    }

    private HttpHeaders getDefaultHeaders() {
        HttpHeaders defaultHeaders = new HttpHeaders();
        defaultHeaders.add("Content-Type", "application/json");
        defaultHeaders.add("Host", "api.telegram.org");
        defaultHeaders.add("accept-encoding", "gzip, deflate");
        defaultHeaders.add("Accept", "*/*");
        return defaultHeaders;
    }

}
