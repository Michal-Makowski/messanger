package com.makowski.messenger.exception;

public class NotMatchException extends RuntimeException{
    
    public NotMatchException(){
        super("Wrong Password");
    }
    
    public NotMatchException(String field){
        super("'" + field + "' in first and second field does match");
    }
}
