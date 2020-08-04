package com.venesa.service;

import com.venesa.entity.Message;


import com.venesa.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    public Message getUserByUsername(int messageId) throws UsernameNotFoundException {
        Message message = messageRepository.findByMessageId(messageId);
        if(message == null) {
            throw new UsernameNotFoundException("Message not found with id: " + messageId);
        }
        return message;
    }
}
