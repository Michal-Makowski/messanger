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

import com.makowski.messenger.entity.Message;
import com.makowski.messenger.exception.ErrorResponse;
import com.makowski.messenger.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "Message Controller", description = "Manage Message enitity")
@AllArgsConstructor
@RestController
@RequestMapping("/message")
public class MessageController {
    
    private MessageService messageService;

    @Operation(summary = "Send message", description = "An authenticated user can send a message to one or more users. Every time when User sends first message to another User (or group of Users) API automatically create new Chat.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User have successfully sent message to one or more users"),
        @ApiResponse(responseCode = "400", description = "User can't send message. Check Request Body, some field can be not valid", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404", description = "User can't send message. One or more Users with {username} don't exist in our database", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @PostMapping(value = "/sendTo/{usernames}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> sendMessage(@Valid @RequestBody Message message, @PathVariable List<String> usernames){
        return new ResponseEntity<>(messageService.sendMessage(message, usernames), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Chat", description = "An authenticated user can get chat between another User (or group of Users) and himself. When chat between Users don't exist, create new empty chat.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User have successful received messages between him and another user (or group of users). Messages are sorted by created date. ", content = @Content(schema = @Schema(implementation = Message.class))),
        @ApiResponse(responseCode = "404", description = "User can't received a message. One or more Users with {username} don't exist in our database", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @GetMapping(value = "/getChat/{usernames}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Message>> getChat(@PathVariable List<String> usernames){
        return new ResponseEntity<>(messageService.getChat(usernames), HttpStatus.OK);
    }

    @Operation(summary = "Delete message", description = "An authenticated user can delete his own message")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User have successful removed message from database", content = @Content()),
        @ApiResponse(responseCode = "404", description = "User can't remove message with {id} because she doesn't exist in our database", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "User can't remove message with {id} because the message does not belong to him", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> deleteMessage(@PathVariable Long id){
        messageService.deleteMessage(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Edit message", description = "An authenticated user can edit his own message")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User have successful edit his message", content = @Content(schema = @Schema(implementation = Message.class))),
        @ApiResponse(responseCode = "404", description = "User can't edit message with {id} because it doesn't exist in our database", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403", description = "User can't edit message with {id} because the message does not belong to him", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @PutMapping(value = "/edit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> editMessage(@Valid @RequestBody Message editMessage, @PathVariable Long id){
        return new ResponseEntity<>(messageService.editMessage(editMessage, id), HttpStatus.OK);
    }
}