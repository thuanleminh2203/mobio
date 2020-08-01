package com.venesa.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.venesa.dto.Customer;

@Service
public class RabbitMQSender {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Value("${venesa.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${venesa.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(Customer customer) {
		rabbitTemplate.convertAndSend(exchange, routingkey, customer);
	}
}
