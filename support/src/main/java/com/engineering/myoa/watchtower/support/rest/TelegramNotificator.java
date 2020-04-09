package com.engineering.myoa.watchtower.support.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.engineering.myoa.watchtower.support.dto.TelegramMessage;
import com.engineering.myoa.watchtower.support.sensitive.SensitiveTokenProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * TelegramNotificator
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-08
 *
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "sensitive.init", havingValue = "true")
public class TelegramNotificator {

    private static final String SEND_METHOD = "sendMessage";
    private static final String SENSITIVE_DATA_BOT_TOKEN_NAME = "telegram-bot";

    private final RestTemplate restTemplate;
    private final String telegramBotApi;
    private final HttpHeaders defaultHeaders;
    private final String botToken;

    public TelegramNotificator(RestTemplate restTemplate,
                               @Value("${telegram-bot-api}") String telegramBotApi,
                               SensitiveTokenProperties sensitiveTokenProperties) {
        this.restTemplate = restTemplate;
        this.telegramBotApi = telegramBotApi;
        defaultHeaders = getDefaultHeaders();
        this.botToken = sensitiveTokenProperties.findByTokenName(SENSITIVE_DATA_BOT_TOKEN_NAME).getTokenValue();
    }

    @Async("asyncThreadPoolTaskExecutor")
    public void notifyMessage(String chatId, String message) {

        restTemplate.exchange(getUriString(),
                              HttpMethod.POST,
                              getRequestEntity(chatId, message),
                              Map.class);
    }

    private String getUriString() {
        return UriComponentsBuilder
                .fromUriString(String.format("%s%s/%s", telegramBotApi, this.botToken, SEND_METHOD))
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
