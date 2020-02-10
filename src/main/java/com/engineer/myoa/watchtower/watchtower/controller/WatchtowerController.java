package com.engineer.myoa.watchtower.watchtower.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.myoa.watchtower.watchtower.component.WatchtowerMessageProducer;
import com.engineer.myoa.watchtower.watchtower.dto.NotifyMessage;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping(value = "/watchtower")
public class WatchtowerController {

	@Autowired
	private WatchtowerMessageProducer watchtowerMessageProducer;

	@PostMapping("/telegram/messages")
	public Mono<ResponseEntity<String>> sendMessage(ServerHttpRequest request,
		@RequestBody NotifyMessage message) {

		CommonResponse<String> response = null;
		try {
			watchtowerMessageProducer.produceTelegramMessage(message);
			response = CommonResponse.success("produced");
		} catch (IOException e) {
			e.printStackTrace();
			response = CommonResponse.success("produce failed", HttpStatus.INTERNAL_SERVER_ERROR);

		}

		Mono.just(response);
		return ResponseEntity.ok(response);
	}
}
