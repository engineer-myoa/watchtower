package com.engineer.myoa.watchtower.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.StatefulRetryOperationsInterceptor;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {
	public static final String EXCHANGE_NAME = "watchtower";
	public static final String QUEUE_NAME = EXCHANGE_NAME + "-telegram";

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory();
		factory.setHost("192.168.0.100");
		factory.setUsername("watchtower");
		factory.setPassword("watchtower");
		return factory;
	}

	@Bean
	public Jackson2JsonMessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
		rabbitTemplate.setRoutingKey(QUEUE_NAME);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public SimpleMessageListenerContainer messageListener() {
		SimpleMessageListenerContainer messageListener = new SimpleMessageListenerContainer();
		messageListener.setConnectionFactory(connectionFactory());
		messageListener.setQueueNames(QUEUE_NAME);
		messageListener.setRecoveryInterval(1000L);
		messageListener.setRetryDeclarationInterval(1000L);
		messageListener.setDeclarationRetries(3);
		return messageListener;
	}

	@Bean
	public Queue queue() {
		return new Queue(QUEUE_NAME, false);
	}

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(EXCHANGE_NAME);
	}

	@Bean
	public Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
	}

	@Bean
	StatefulRetryOperationsInterceptor interceptor() {
		return RetryInterceptorBuilder.stateful()
			.maxAttempts(5)
			.backOffOptions(1000, 2.0, 10000)
			.build();
	}
}
