package com.engineer.myoa.watchtower.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.StatelessRetryOperationsInterceptorFactoryBean;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.engineer.myoa.watchtower.watchtower.component.MQRecoverer;
import com.engineer.myoa.watchtower.watchtower.component.WatchtowerMessageConsumer;

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
		rabbitTemplate.setRetryTemplate(retryTemplate());
		return rabbitTemplate;
	}

	@Bean("MQcontainerFactory")
	public SimpleRabbitListenerContainerFactory messageListenerContainer() {
		SimpleRabbitListenerContainerFactory messageListenerContainer = new SimpleRabbitListenerContainerFactory();
		messageListenerContainer.setConnectionFactory(connectionFactory());
		messageListenerContainer.setMessageConverter(jsonMessageConverter());
		messageListenerContainer.setAutoStartup(true);
		messageListenerContainer.setPrefetchCount(1);
		messageListenerContainer.setConcurrentConsumers(1);
		messageListenerContainer.setAdviceChain(retryInterceptor());
		return messageListenerContainer;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(WatchtowerMessageConsumer consumer) {
		return new MessageListenerAdapter(consumer, "consumeTelegramMessage");
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

	/**
	 * Requeue message when failed consiming even create RetryTemplate bean, even reached max retry attemption.
	 * So need to restrict requeueing, covered exception. This retryInterceptor bean will be proceed.
	 * And register retryInterceptor in listener's AOP advice chaning
	 *
	 * @return {@link RetryOperationsInterceptor}
	 */
	@Bean
	public RetryOperationsInterceptor retryInterceptor() {
		StatelessRetryOperationsInterceptorFactoryBean retryInterceptor = new StatelessRetryOperationsInterceptorFactoryBean();

		// Set (Custom)Recoverer, retryTemplate
		retryInterceptor.setMessageRecoverer(mqRecoverer());
		retryInterceptor.setRetryOperations(retryTemplate());
		return retryInterceptor.getObject();
	}

	/**
	 * Custom message queue recoverer that extends {@link RejectAndDontRequeueRecoverer}
	 * Recoverer can receive message and exception message
	 * Warning: {@link RejectAndDontRequeueRecoverer} reject requeueing.
	 * If you want requeue that message(s), Change inherited class or re-send(produce) message(s).
	 *
	 * @return {@link MQRecoverer}
	 */
	@Bean
	public MQRecoverer mqRecoverer() {
		return new MQRecoverer();
	}

	/**
	 * RabbitMQ will tried to push messages to subscriber. Even not want to that.
	 * Because default RabbitMQ configurations are contained ack mode.
	 * Consumer fail to process. Next, consumer not send acknowledge.
	 * That persuade message(s) keep alive in specific queue.
	 * So restrict max attempt count, set retry interval like Round-Robbin.
	 *
	 * @return {@link RetryTemplate}
	 */
	@Bean
	public RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
		retryTemplate.setRetryPolicy(new SimpleRetryPolicy(5));

		// Default backoff-policy has 3 times restriction
		ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
		backOffPolicy.setInitialInterval(1000L);
		backOffPolicy.setMultiplier(2.0D);
		backOffPolicy.setInitialInterval(10000L);
		retryTemplate.setBackOffPolicy(backOffPolicy);
		return retryTemplate;
	}
}
