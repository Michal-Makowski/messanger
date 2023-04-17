package com.makowski.messenger.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makowski.messenger.entity.Chat;
import com.makowski.messenger.exception.ErrorResponse;
import com.makowski.messenger.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;


@Tag(name = "3. Chat Controller", description = "Manage Chat enitity")
@AllArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatController {
    
    private ChatService chatService;

    @Operation(summary = "Get chats", description = "Return all user's chats")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User have successfully received all his chats",content = @Content(array = @ArraySchema (schema = @Schema(implementation = Chat.class)))),
        @ApiResponse(responseCode = "404", description = "User don't have any chats in our database", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Chat>> getMyChats(){
        return new ResponseEntity<>(chatService.getMyChats(), HttpStatus.OK);
    }
}
