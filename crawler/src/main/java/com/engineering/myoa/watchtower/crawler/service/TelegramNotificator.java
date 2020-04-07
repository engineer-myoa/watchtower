package com.engineering.myoa.watchtower.crawler.service;

import org.springframework.stereotype.Component;

/**
 * TelegramNotificator
 * @author Shin Woo-jin (lp12254@linecorp.com)
 * @since 2020-04-07
 *
 */
@Component
public class TelegramNotificator {
//
//    public CommonResponse<String> sendTelegramMessage(NotifyMessage messageDto) {
//        log.info("URL : {}", botApiUrl);
//
//        String methodName = "sendMessage";
//        String token = messageDto.getToken();
//
//        if (token == null || token.isEmpty()) {
//            return CommonResponse.fail("Token is null or empty", HttpStatus.UNAUTHORIZED);
//        }
//
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder
//                .fromUriString(String.format("%s%s/%s", botApiUrl, token, methodName));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/json");
//        headers.add("Host", "api.telegram.org");
//
//        if (messageDto.getChatIdList() == null || messageDto.getChatIdList().isEmpty()) {
//            return CommonResponse.fail("chatIdList is null or empty", HttpStatus.BAD_REQUEST);
//        }
//        Stream<String> parallelStream = messageDto.getChatIdList().parallelStream();
//        parallelStream.forEach(chatId -> {
//            TelegramMessage telegramMessage = new TelegramMessage(chatId, messageDto.getMessage());
//            HttpEntity<TelegramMessage> requestBody = new HttpEntity<TelegramMessage>(telegramMessage, headers);
//            log.info("REQUEST URI : {}", uriBuilder.toUriString());
//            log.info("REQUEST BODY : {}", requestBody);
//
//            ResponseEntity response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST,
//                                                            requestBody, Map.class);
//            if (response.getStatusCode() != HttpStatus.OK) {
//                log.info("ERROR : {}, {}", response.getStatusCode(), response.getBody());
//                return;
//            }
//
//            log.info("BODY : {}", response.getBody());
//            return;
//        });
//
//        return CommonResponse.success("success");
//    }
}
