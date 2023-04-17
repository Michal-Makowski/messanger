package com.makowski.messenger.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makowski.messenger.dto.ChangeEmailDto;
import com.makowski.messenger.dto.ChangePasswordDto;
import com.makowski.messenger.dto.UpdateUserDto;
import com.makowski.messenger.entity.User;
import com.makowski.messenger.exception.ErrorResponse;
import com.makowski.messenger.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "1. User Controller", description = "Manage User enitity")
@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Operation(summary = "Create User", description = "Creates a User from the provided payload")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User is successfully created and saved in Database"),
        @ApiResponse(responseCode = "400", description = "User not created, check Request Body some field can be not valid, or Request Body may be not complete", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
    })
    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @Operation(summary = "Find user", description = "Search user by username in our database. Search begin from 1 letter and ignore case. Return list of users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Search successfully, return list with results",content = @Content(array = @ArraySchema (schema = @Schema(implementation = User.class)))),
        @ApiResponse(responseCode = "404", description = "User with {username} don't exist in our database", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findUser(@PathVariable String username){
        return new ResponseEntity<>(userService.findUser(username),HttpStatus.OK);
    }

    @Operation(summary = "Update User", description = "Update User Information (only first and last name). Every User can change information only about himself. User must be authenticated.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "202", description = "User have successfully updated his information (firs and last name)",content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "User can't be updated. Check Request Body, some field can be not valid, or Request Body may be not complete", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto){
        return new ResponseEntity<>(userService.updateUser(updateUserDto), HttpStatus.OK);
    }

    @Operation(summary = "Change password", description = "Change User password. User can change only his own password and need to provide his old password as well. User must be authenticated.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User have successfully change his password", content = @Content(schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "User can't change his password. Check Request Body, some field can be not valid, or entered password is incorrect", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @PutMapping(value = "/changePassword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto){
        return new ResponseEntity<>(userService.changePassword(changePasswordDto), HttpStatus.OK);
    }
    
    @Operation(summary = "Change e-mail", description = "Change User e-mail. User can change only his own e-mail and need to enter his password as well. User must be authenticated.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User have successfully change his e-mail", content = @Content (schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "400", description = "User can't change his e-mail. Check Request Body, some field can be not valid, or entered password is incorrect", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @PutMapping(value = "/changeEmail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> changeEmail(@Valid @RequestBody ChangeEmailDto changeEmailDto){
        return new ResponseEntity<>(userService.changeEmail(changeEmailDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete User", description = "Remove User from database. User can remove only himself from database. User must be authenticated.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User have successfully removed himself from database", content = @Content()),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> deleteUser(){
        userService.deleteUser();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
