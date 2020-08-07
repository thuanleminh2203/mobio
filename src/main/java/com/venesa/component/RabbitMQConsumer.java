package com.venesa.component;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.venesa.dto.Customer;

@Component
public class RabbitMQConsumer {
	
//	@RabbitListener(queues = "${venesa.rabbitmq.queue}")
	public void receivedMessage(Message message) {
		System.out.println("Received Message From RabbitMQ- " + new String(message.getBody()));
	}
}
