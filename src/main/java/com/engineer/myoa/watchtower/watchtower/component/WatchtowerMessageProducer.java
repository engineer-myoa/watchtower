package com.engineer.myoa.watchtower.watchtower.component;

import java.io.IOException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.engineer.myoa.watchtower.configuration.RabbitMQConfiguration;
import com.engineer.myoa.watchtower.watchtower.dto.NotifyMessage;

@Component
public class WatchtowerMessageProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void produceTelegramMessage(NotifyMessage notifyMessage) throws IOException {
		rabbitTemplate.convertAndSend(RabbitMQConfiguration.QUEUE_NAME, notifyMessage);
	}
}
