package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

public class MessageService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository) {
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }
    private final static int MAXIMUM_CHARACTER_LENGTH = 255;

    public Message createMessage(Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > MAXIMUM_CHARACTER_LENGTH || 
            accountRepository.findById(message.getPostedBy()).isPresent()) {
                throw new IllegalArgumentException("error creating message");
        }
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getOneMessage(int message_id) {
        return messageRepository.findById(message_id).get();
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
        if (updatedMessage.isEmpty() || message_text.length() > MAXIMUM_CHARACTER_LENGTH) {
            throw new IllegalArgumentException("error updating message!");
        }
        updatedMessage.get().setMessageText(message_text);
        return 1;
    }

    public List<Message> getAllMessagesGivenId(int account_id) {
        return messageRepository.findAllByPosted_by(account_id);
    }
    
}
