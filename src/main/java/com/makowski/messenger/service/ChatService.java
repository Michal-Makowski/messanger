package com.makowski.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.makowski.messenger.entity.Chat;
import com.makowski.messenger.entity.User;
import com.makowski.messenger.exception.EntityNotFoundException;
import com.makowski.messenger.repository.ChatRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ChatService {
    
    private ChatRepository chatRepository;
    private UserService userService;

    public Chat createChat(List<User> users){
        Chat chat = new Chat();
        chat.setChatUsers(users);
        return chatRepository.save(chat);   
    }

    public List<Chat> getMyChats(){
        User user = userService.extractUser();
        List<Chat> chats = chatRepository.findByChatUsersId(user.getId())
            .orElseThrow(() -> new EntityNotFoundException());
        return chats; 
    }

    //Check if Chat with all user from request exist in our database and return Chat otherwise make new Chat
    public Chat getChat(User user, List<User> users){
        //Get from database every Chat with user. 
        Optional<List<Chat>> optionalChats = chatRepository.findByChatUsersId(user.getId());
        if(optionalChats.isPresent()){
            List<Chat> chats = optionalChats.get();    
            //Loop looking for Chat with all users from request. After successful return Chat from database otherwise create new Chat
            for(int i = 0; i < chats.size(); i++){
                if(chatUsersComparison(chats.get(i).getChatUsers(), users)){
                    return chats.get(i);
                }
            }
        } 
        return createChat(users);
    }
    //Check if Chat from database have the same users from request
    public boolean chatUsersComparison(List<User> firstList, List<User> secondList){
        if(firstList.size() == secondList.size()){
            List<User> tempList = new ArrayList<>(firstList);
            for (User user: secondList){
                if( !tempList.remove(user)){
                    return false;
                }
            } return tempList.isEmpty();
        } return false;
    }
}