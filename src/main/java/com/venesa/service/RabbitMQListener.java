package com.venesa.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    //	@Override
    @RabbitListener(queues = "queue")
    public void onMessage(Message message) {
        System.out.println("Consuming Message - " + new String(message.getBody()));
//		if(message.getBirthday() == null) {
//			throw new Exception("=========== dead letter=========");
//		}
    }
}
