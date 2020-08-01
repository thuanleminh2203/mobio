//package com.venesa.config;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.venesa.service.RabbitMQListner;
//
//
//@Configuration
//public class RabbitMQListenConfig {
//	
//	@Value("${venesa.rabbitmq.queue}")
//	String queueName;
//
//	@Value("${spring.rabbitmq.username}")
//	String username;
//
//	@Value("${spring.rabbitmq.password}")
//	private String password;
//	
//	@Bean
//	Queue queue() {
//		return new Queue(queueName, false);
//	}
//	
//	@Bean
//	MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
//		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//		simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
//		simpleMessageListenerContainer.setQueues(queue());
//		simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
//		return simpleMessageListenerContainer;
//
//	}
//    
//    //create custom connection factory
////	@Bean
////	ConnectionFactory connectionFactory() {
////		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
////		cachingConnectionFactory.setUsername(username);
////		cachingConnectionFactory.setUsername(password);
////		return cachingConnectionFactory;
////	}
//	
//    //create MessageListenerContainer using custom connection factory
//	/*@Bean
//	MessageListenerContainer messageListenerContainer() {
//		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
//		simpleMessageListenerContainer.setConnectionFactory(connectionFactory());
//		simpleMessageListenerContainer.setQueues(queue());
//		simpleMessageListenerContainer.setMessageListener(new RabbitMQListner());
//		return simpleMessageListenerContainer;
//
//	}*/
//	
////	@Bean
////	Queue queue() {
////		return new Queue(queueName, false);
////	}
//}
