package com.engineer.myoa.watchtower.watchtower.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engineer.myoa.watchtower.property.AbstractController;
import com.engineer.myoa.watchtower.property.CommonResponse;
import com.engineer.myoa.watchtower.watchtower.component.WatchtowerMessageProducer;
import com.engineer.myoa.watchtower.watchtower.dto.NotifyMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/watchtower")
public class WatchtowerController extends AbstractController {

	@Autowired
	private WatchtowerMessageProducer watchtowerMessageProducer;

	@PostMapping("/telegram/messages")
	public ResponseEntity<CommonResponse<String>> sendMessage(HttpServletRequest request,
		@RequestBody NotifyMessage message) {

		CommonResponse<String> response = null;
		try {
			watchtowerMessageProducer.produceTelegramMessage(message);
			response = CommonResponse.success("produced");
		} catch (IOException e) {
			e.printStackTrace();
			response = CommonResponse.success("produce failed", HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return makeResponse(request, response, response.getHttpStatus());
	}
}
