package com.engineer.myoa.watchtower.watchtower.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.myoa.watchtower.configuration.RabbitMQConfiguration;
import com.engineer.myoa.watchtower.property.AbstractController;
import com.engineer.myoa.watchtower.property.CommonResponse;
import com.engineer.myoa.watchtower.watchtower.dto.NotifyMessage;
import com.engineer.myoa.watchtower.watchtower.dto.TelegramMessage;
import com.engineer.myoa.watchtower.watchtower.service.WatchtowerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/watchtower")
public class WatchtowerController extends AbstractController {

	@Autowired
	private WatchtowerService watchtowerService;

	@PostMapping("/messages")
	public ResponseEntity<CommonResponse<String>> sendMessage(HttpServletRequest request,
		@RequestBody NotifyMessage message) {

		CommonResponse<String> response = watchtowerService.sendMessage(message);
		return makeResponse(request, response);
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@GetMapping("/")
	public ResponseEntity<CommonResponse<String>> test(HttpServletRequest request) {
		rabbitTemplate.convertAndSend(RabbitMQConfiguration.QUEUE_NAME, new TelegramMessage("123123", "HIHI"));

		return makeResponse(request, new CommonResponse<>());
	}
}
