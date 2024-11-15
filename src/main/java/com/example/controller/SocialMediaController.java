package com.example.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> postUser(@RequestBody Account account) {
        Account createdAccount = accountService.registerAccount(account);
        return ResponseEntity.status(HttpStatus.OK).body(createdAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account) {
         Account loginAccount = accountService.login(account);
         return ResponseEntity.status(HttpStatus.OK).body(loginAccount);
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.status(HttpStatus.OK).body(createdMessage);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(allMessages);
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getOneMessage(@PathVariable int message_id) {
        Optional<Message> oneMessage = messageService.getOneMessage(message_id);
        if (oneMessage.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(oneMessage.get());
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id) {
        int rowsAffected = messageService.deleteMessage(message_id);
        if (rowsAffected > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(rowsAffected);
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> patchMessage(@PathVariable int message_id, @RequestBody Message message) {
        String messageText = message.getMessageText();
        int rowsAffected = messageService.updateMessage(message_id, messageText);
    
        return ResponseEntity.status(HttpStatus.OK).body(rowsAffected);
    }
    

    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesGivenAccountId(@PathVariable int account_id) {
        List<Message> messagesGivenAccountId = messageService.getAllMessagesGivenId(account_id);
        return ResponseEntity.status(HttpStatus.OK).body(messagesGivenAccountId);
    }
}
