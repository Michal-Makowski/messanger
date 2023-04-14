package com.makowski.messenger.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makowski.messenger.entity.Message;
import com.makowski.messenger.service.MessageService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {
    
    private MessageService messageService;

    @PostMapping("/sendTo/{usernames}")
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody Message message, @PathVariable List<String> usernames){
        return new ResponseEntity<>(messageService.sendMessage(message, usernames), HttpStatus.CREATED);
    }

    @GetMapping("/getChat/{usernames}")
    public ResponseEntity<List<Message>> getChat(@PathVariable List<String> usernames){
        return new ResponseEntity<>(messageService.getChat(usernames), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Message> deleteMessage(@PathVariable Long id){
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Message> editMessage(@Valid @RequestBody Message editMessage, @PathVariable Long id){
        return new ResponseEntity<>(messageService.editMessage(editMessage, id), HttpStatus.OK);
    }
}