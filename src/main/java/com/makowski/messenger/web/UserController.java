package com.makowski.messenger.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makowski.messenger.dto.ChangeEmailDto;
import com.makowski.messenger.dto.ChangePasswordDto;
import com.makowski.messenger.dto.UpdateUserDto;
import com.makowski.messenger.entity.User;
import com.makowski.messenger.service.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto){
        return new ResponseEntity<>(userService.updateUser(updateUserDto), HttpStatus.OK);
    }

    @PutMapping("/changePassword")
    public ResponseEntity<User> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto){
        return new ResponseEntity<>(userService.changePassword(changePasswordDto), HttpStatus.OK);
    }
    
    @PutMapping("/changeEmail")
    public ResponseEntity<User> changeEmail(@Valid @RequestBody ChangeEmailDto changeEmailDto){
        return new ResponseEntity<>(userService.changeEmail(changeEmailDto), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(){
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
