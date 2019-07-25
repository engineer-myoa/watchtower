package com.engineer.myoa.watchtower.watchtower.component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.engineer.myoa.watchtower.configuration.RabbitMQConfiguration;
import com.engineer.myoa.watchtower.watchtower.dto.TelegramMessage;

@Component
public class WatchtowerMessageConsumer {

	@RabbitListener(queues = RabbitMQConfiguration.QUEUE_NAME)
	public void consumeTelegramMessage(TelegramMessage message) {
		System.out.println("------------------------------------");
		System.out.println("Consuming.....");
		System.out.println(message.toString());
	}
}
