package com.engineer.myoa.watchtower.watchtower.component;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MQRecoverer extends RejectAndDontRequeueRecoverer {

	@Override
	public void recover(Message message, Throwable cause) {
		super.recover(message, cause);
		log.info("FAILED message consuming...");

	}
}
