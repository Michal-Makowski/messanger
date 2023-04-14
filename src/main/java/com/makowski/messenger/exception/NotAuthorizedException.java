package com.makowski.messenger.exception;

public class NotAuthorizedException extends RuntimeException{
    
    public NotAuthorizedException(String operation){
        super("You can '" + operation + "' only you own Message!");
    }
}
