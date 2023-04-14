package com.makowski.messenger.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.makowski.messenger.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



@Component
public class UniqueValidation implements ConstraintValidator<Unique, String>{
 
   
    private UserRepository userRepository;

    public UniqueValidation(){
    }

    @Autowired
    public UniqueValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(Unique arg0) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(userRepository == null)
            return true;
        if(userRepository.findByEmail(value).isPresent()) return false;
        if(userRepository.findByUsername(value).isPresent()) return false;
        return true;
    }
    
}
