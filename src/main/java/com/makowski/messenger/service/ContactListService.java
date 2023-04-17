package com.makowski.messenger.service;


import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.makowski.messenger.entity.ContactList;
import com.makowski.messenger.entity.User;
import com.makowski.messenger.exception.EntityNotFoundException;
import com.makowski.messenger.repository.ContactListRepository;

import lombok.AllArgsConstructor;

//to avoid Circular Dependencies onConstructor = @__(@Lazy)
@AllArgsConstructor(onConstructor = @__(@Lazy))
@Service
public class ContactListService {
    
    private ContactListRepository contactListReository;
    @Lazy
    private UserService userService;

    public ContactList createContactList(User user){
        ContactList contactList = new ContactList();
        contactList.setUser(user);
        return contactListReository.save(contactList);
    }

    public ContactList addUserToContactList(String usename){
        ContactList contactList = extractConstactList();
        User user = userService.getUserByUsername(usename);
        if(!contactList.getUsers().contains(user)) contactList.getUsers().add(user);       
        return contactListReository.save(contactList);
    }

    public ContactList removeUserFromContactList(String username){
        ContactList contactList = extractConstactList();
        User user = userService.getUserByUsername(username);
        if(contactList.getUsers().contains(user)){
            contactList.getUsers().remove(user);
        } else {
            throw new EntityNotFoundException(user.getUsername(), ContactList.class);
        }
        return contactListReository.save(contactList);
    }

    public ContactList getContactList(){
        return extractConstactList();
    }

    public ContactList extractConstactList(){
        Long userId = userService.extractUser().getId(); 
        return contactListReository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException(userId, ContactList.class));
    }
}
