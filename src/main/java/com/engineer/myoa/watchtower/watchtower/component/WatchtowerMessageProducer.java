package com.engineer.myoa.watchtower.watchtower.component;

import static com.engineer.myoa.watchtower.configuration.RabbitListenerConfiguration.*;

import java.io.IOException;

import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.stereotype.Component;

import com.engineer.myoa.watchtower.watchtower.dto.NotifyMessage;

@Component
public class WatchtowerMessageProducer {

	private final AsyncRabbitTemplate rabbitTemplate;

	public WatchtowerMessageProducer(AsyncRabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void produceTelegramMessage(NotifyMessage notifyMessage) throws IOException {
		rabbitTemplate.convertSendAndReceive(QUEUE_NAME, notifyMessage, m -> {
			m.getMessageProperties().getHeaders().remove("__TypeId__");
			return m;
		});
	}
}
