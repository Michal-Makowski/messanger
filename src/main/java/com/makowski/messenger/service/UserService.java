package com.makowski.messenger.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.makowski.messenger.dto.ChangeEmailDto;
import com.makowski.messenger.dto.ChangePasswordDto;
import com.makowski.messenger.dto.UpdateUserDto;
import com.makowski.messenger.entity.User;
import com.makowski.messenger.exception.EntityNotFoundException;
import com.makowski.messenger.exception.NotMatchException;
import com.makowski.messenger.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ContactListService contactListService;

    public User createUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        contactListService.createContactList(user);
        return user;
    }

    public User updateUser(UpdateUserDto updateUserDto){
        User user = extractUser();
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        return userRepository.save(user);
    }

    public void deleteUser(){
        User user = extractUser();
        userRepository.delete(user);
    }

    public List<User> findUser(String username){
        return userRepository.findByUsernameContainingIgnoreCase(username)
            .orElseThrow(() -> new EntityNotFoundException(username));
    }

    public User changePassword(ChangePasswordDto changePasswordDto){
        User user = extractUser();
        if(changePasswordDto.getNewPassword().equals(changePasswordDto.getRepeatNewPassword())){
            if(bCryptPasswordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())){
                user.setPassword(bCryptPasswordEncoder.encode(changePasswordDto.getNewPassword()));
            } else {
                throw new NotMatchException();
            }
        } else {
            throw new NotMatchException("Password");
        }
        return userRepository.save(user);
    }

    public User changeEmail(ChangeEmailDto changeEmailDto){
        User user = extractUser();
        if(changeEmailDto.getNewEmail().equals(changeEmailDto.getRepeatNewEmail())){
            if(bCryptPasswordEncoder.matches(changeEmailDto.getPassword(), user.getPassword())){
                user.setEmail(changeEmailDto.getNewEmail());
            } else {
                throw new NotMatchException();
            }
        } else {
            throw new NotMatchException("Email");
        }
        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(username));
    }
    //Get authenticated user from SecurityContextHolder. So we can check who make request.
    public User extractUser(){
        String authUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getUserByUsername(authUsername);
        return user;
    }

}
