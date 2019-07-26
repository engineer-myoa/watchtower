package com.engineer.myoa.watchtower.watchtower.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engineer.myoa.watchtower.configuration.RabbitMQConfiguration;
import com.engineer.myoa.watchtower.watchtower.dto.NotifyMessage;
import com.engineer.myoa.watchtower.watchtower.service.WatchtowerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WatchtowerMessageConsumer {

	@Autowired
	WatchtowerService watchtowerService;

	@RabbitListener(containerFactory = "MQcontainerFactory", queues = {RabbitMQConfiguration.QUEUE_NAME})
	public void consumeTelegramMessage(NotifyMessage message) {
		log.info("Consuming..... {}", message);
		watchtowerService.sendTelegramMessage(message);
	}
}
