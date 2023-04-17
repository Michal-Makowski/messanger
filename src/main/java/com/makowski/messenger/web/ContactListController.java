package com.makowski.messenger.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.makowski.messenger.entity.ContactList;
import com.makowski.messenger.exception.ErrorResponse;
import com.makowski.messenger.service.ContactListService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
@Tag(name = "4. Contact list Controller", description = "Manage ContactList enitity")
@AllArgsConstructor
@RestController
@RequestMapping("/contactList")
public class ContactListController {
    
    private ContactListService contactListService;

    @Operation(summary = "Add user to contact list", description = "An authenticated user can add another user to his contact list.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User have successfully add another user with {username} to his contact list", content = @Content(schema = @Schema(implementation = ContactList.class))),
        @ApiResponse(responseCode = "404", description = "User can't add another user with {username} to his contact list, because user with {username} don't exist in our database", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @PostMapping(value = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactList> addUserToContactList(@PathVariable String username){
        return new ResponseEntity<>(contactListService.addUserToContactList(username), HttpStatus.OK);
    }

    @Operation(summary = "Get user contact list", description = "An authenticated user can get his contact list.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User have successfully got his contact list", content = @Content(schema = @Schema(implementation = ContactList.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<ContactList> getContactList(){
        return new ResponseEntity<>(contactListService.getContactList(), HttpStatus.OK);
    }

    @Operation(summary = "Remove user from contact list", description = "An authenticated user can remove another user from his contact list.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User have successfully remove another user with {username} from his contact list", content = @Content),
        @ApiResponse(responseCode = "404", description = "User can't remove another user with {username} from his contact list, because user with {username} don't exist in our database", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),  
        @ApiResponse(responseCode = "401", description = "User is not authenticated, or have not valid JWT", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))  
    })
    @DeleteMapping(value = "/{username}")
    public ResponseEntity<ContactList> removeUserFromContactList(@PathVariable String username){
        contactListService.removeUserFromContactList(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
