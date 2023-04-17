package com.makowski.messenger.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.makowski.messenger.entity.Chat;
import com.makowski.messenger.entity.Message;
import com.makowski.messenger.entity.User;
import com.makowski.messenger.exception.EntityNotFoundException;
import com.makowski.messenger.exception.NotAuthorizedException;
import com.makowski.messenger.repository.MessageRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@EnableScheduling
public class MessageService {

    private MessageRepository messageRepository;
    private UserService userService;
    private ChatService chatService;
    

    public Message sendMessage(Message message, List<String> usernames){
        User user = userService.extractUser();
        message.setUser(user);
        message.setCreatedAt(LocalDateTime.now());
        message.setChat(chatService.getChat(user, convertUsernamesToUsers(usernames)));
        if(!message.isPermament()){
            message.setExpiredAt(LocalDateTime.now().plusWeeks(1));
        }
        return messageRepository.save(message);
    }
    
    public List<Message> getChat(List<String> usernames){
        User user = userService.extractUser();
        Chat chat = chatService.getChat(user, convertUsernamesToUsers(usernames));
        List<Message> sortedMessages = chat.getMessages();
        sortedMessages.sort(Comparator.comparing(Message :: getCreatedAt));
        return sortedMessages;
    }
    
    public void deleteMessage(Long id){
        if(!getMessage(id).getUser().equals(userService.extractUser())) throw new NotAuthorizedException("delete");
            messageRepository.deleteById(id);
    }
    
    public Message editMessage(Message editMessage, Long id){
        Message message = getMessage(id);
        if(!message.getUser().equals(userService.extractUser())) throw new NotAuthorizedException("edit");
        message.setText(editMessage.getText());
        if(!editMessage.isPermament()){
            message.setExpiredAt(LocalDateTime.now().plusWeeks(1));
            message.setPermament(false);
        } else {
            message.setExpiredAt(null);
            message.setPermament(true);
        }
        return messageRepository.save(message);
    }

    public Message getMessage(Long id){
        return messageRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id, Message.class));
    }
    //Convert List<String> with usernames (from request @PathVariable) to List<User>  and add to the list User who make request.
    public List<User> convertUsernamesToUsers(List<String> usernames){
        List<User> chatUsers = new ArrayList<>();
        User user = userService.extractUser();
        chatUsers.add(user);
        usernames.forEach(username -> {
            chatUsers.add(userService.getUserByUsername(username));
        });
        return chatUsers;
    }

    //Automatically every 30 min check message expiration date and remove from database
    @Scheduled(fixedDelay = 1800000)
    public void deleteExpiredMessage(){
        List<Message> messages = messageRepository.findByPermament(false);
        messages.forEach(message ->{
            if(message.getExpiredAt().isBefore(LocalDateTime.now())){
                messageRepository.delete(message);
            }
        });
    }  
}
