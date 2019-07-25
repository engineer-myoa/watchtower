package com.engineer.myoa.watchtower.watchtower.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.engineer.myoa.watchtower.property.CommonResponse;
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

	public CommonResponse<String> sendMessage(NotifyMessage message) {
		log.info("URL : {}", botApiUrl);

		String methodName = "sendMessage";
		String token = message.getToken();

		UriComponentsBuilder uriBuilder = ServletUriComponentsBuilder
			.fromUriString(String.format("%s%s/%s", botApiUrl, token, methodName));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Host", "api.telegram.org");
		headers.add("accept-encoding", "gzip, deflate");
		headers.add("Accept", "*/*");

		for (String chatId : message.getChatId()) {

			TelegramMessage telegramMessage = new TelegramMessage(chatId, message.getMessage());

			HttpEntity<TelegramMessage> requestBody = new HttpEntity<TelegramMessage>(telegramMessage, headers);
			ResponseEntity response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.POST, requestBody, Map.class);
			log.info("BODY : {}", response.getBody());
		}

		return CommonResponse.success("null");
	}
}
