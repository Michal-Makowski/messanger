package com.makowski.messenger.validation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidation implements ConstraintValidator<Password, String>{
    
    @Override
    public void initialize(Password arg0) {
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        PasswordValidator passwordValidator = new PasswordValidator(Arrays.asList(
            new LengthRule(8, 100), 
            new CharacterRule(EnglishCharacterData.UpperCase, 1), 
            new CharacterRule(EnglishCharacterData.LowerCase, 1),
            new CharacterRule(EnglishCharacterData.Digit, 1),
            new WhitespaceRule()));
        
        RuleResult result = passwordValidator.validate(new PasswordData(value));
        if (result.isValid()) {
            return true;
        }
        
        List<String> messages = passwordValidator.getMessages(result);    
        String messageTemplate = messages.stream()
            .collect(Collectors.joining(","));
        context.buildConstraintViolationWithTemplate(messageTemplate)
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
    }
}
