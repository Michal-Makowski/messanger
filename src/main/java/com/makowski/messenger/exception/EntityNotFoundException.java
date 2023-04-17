package com.makowski.messenger.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String username) {
        super("User with Username '" + username + "' does not exist in our database ");
    }

    public EntityNotFoundException(Long id, Class<?> entity) {
        super("The " + entity.getSimpleName().toLowerCase() + " with id '" + id + "' does not exist in our records");
    }

    public EntityNotFoundException(String username, Class<?> entity) {
        super("You dont have user with username: '" + username + "' in your '" + entity.getSimpleName().toLowerCase() + "'");
    }

    public EntityNotFoundException() {
        super("You dont have any chat");
    }
}
    
