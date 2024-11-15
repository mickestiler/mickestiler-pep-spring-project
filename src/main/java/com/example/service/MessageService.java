package com.example.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MessageRepository messageRepository;
    private final static int MAXIMUM_CHARACTER_LENGTH = 255;

    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > MAXIMUM_CHARACTER_LENGTH || 
            accountRepository.findById(message.getPostedBy()).isEmpty()) {
                throw new IllegalArgumentException("error creating message");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getOneMessage(int message_id) {
        Optional<Message> oneMessage = messageRepository.findById(message_id);
        return oneMessage;
    }

    public int deleteMessage(int message_id) {
        if (messageRepository.findById(message_id).isPresent()) {
            messageRepository.deleteById(message_id);
            return 1;
        }
        return 0;
    }

    public int updateMessage(int message_id, String message_text) {
        Optional<Message> updatedMessage = messageRepository.findById(message_id);
        if (updatedMessage.isEmpty() || message_text == null || message_text.isBlank() || message_text.length() > MAXIMUM_CHARACTER_LENGTH) {
            throw new IllegalArgumentException("error updating message!");
        }
        Message message = updatedMessage.get();
        message.setMessageText(message_text);
        messageRepository.save(message);
        return 1;
    }

    public List<Message> getAllMessagesGivenId(int account_id) {
        if (accountRepository.findById(account_id).isPresent()) {
            return messageRepository.findAllByPostedBy(account_id);
        }
        return Collections.emptyList();
    }
    
}
