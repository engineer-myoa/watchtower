package com.engineer.myoa.watchtower.watchtower.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.engineer.myoa.watchtower.watchtower.dto.NotifyMessage;
import com.engineer.myoa.watchtower.watchtower.dto.TelegramMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WatchtowerService {

	@Value("${telegram.bot-api}")
	private String botApiUrl;

	@Value("${telegram.chat-id}")
	private List<String> chatIdList;

	@Autowired
	private RestTemplate restTemplate;

	public CommonResponse<String> sendTelegramMessage(NotifyMessage messageDto) {
		log.info("URL : {}", botApiUrl);

		String methodName = "sendMessage";
		String token = messageDto.getToken();

		if (token == null || token.isEmpty()) {
			return CommonResponse.fail("Token is null or empty", HttpStatus.UNAUTHORIZED);
		}

		UriComponentsBuilder uriBuilder = UriComponentsBuilder
			.fromUriString(String.format("%s%s/%s", botApiUrl, token, methodName));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Host", "api.telegram.org");

		if (messageDto.getChatIdList() == null || messageDto.getChatIdList().isEmpty()) {
			return CommonResponse.fail("chatIdList is null or empty", HttpStatus.BAD_REQUEST);
		}
		Stream<String> parallelStream = messageDto.getChatIdList().parallelStream();
		parallelStream.forEach(chatId -> {
			TelegramMessage telegramMessage = new TelegramMessage(chatId, messageDto.getMessage());
			HttpEntity<TelegramMessage> requestBody = new HttpEntity<TelegramMessage>(telegramMessage, headers);
			log.info("REQUEST URI : {}", uriBuilder.toUriString());
			log.info("REQUEST BODY : {}", requestBody);

			ResponseEntity response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, requestBody, Map.class);
			if (response.getStatusCode() != HttpStatus.OK) {
				log.info("ERROR : {}, {}", response.getStatusCode(), response.getBody());
				return;
			}

			log.info("BODY : {}", response.getBody());
			return;
		});

		return CommonResponse.success("success");
	}
}
