package com.venesa.config;

import org.springframework.context.annotation.Configuration;

//import com.venesa.service.RabbitMQListner;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class RabbitMQConfig {
	@Value("${venesa.rabbitmq.queue}")
	String queueName;

	@Value("${venesa.rabbitmq.exchange}")
	String exchange;

	@Value("${venesa.rabbitmq.routingkey}")
	private String routingkey;

	@Bean
	Queue queue() { 
		return new Queue(queueName, false);
	}
 
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange); 
	} 
	
//	@Bean
//	DirectExchange deadLetterExchange() {
//		return new DirectExchange("deadLetterExchange");
//	}
	
//	@Bean
//	Queue dlq() {
//		return QueueBuilder.durable("deadLetter.queue").build();
//	}
//	
//	@Bean
//	Binding DLQbinding() {
//		return BindingBuilder.bind(dlq()).to(deadLetterExchange()).with("deadLetter");
//	}
	
//	@Bean
//	Queue queue1() {
//		return QueueBuilder.durable("javainuse.queue").withArgument("x-dead-letter-exchange", "deadLetterExchange")
//				.withArgument("x-dead-letter-routing-key", "deadLetter").build();
//	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}

	@Bean 
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	} 
	
//	@Bean
//	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
//		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//		simpleMessageListenerContainer.setQueues(queue());
//		simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
//		return simpleMessageListenerContainer;
//
//	}
}
