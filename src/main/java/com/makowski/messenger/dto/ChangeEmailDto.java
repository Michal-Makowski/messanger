package com.makowski.messenger.dto;

import com.makowski.messenger.validation.Unique;
import com.makowski.messenger.validation.ValidationConstans;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ChangeEmailDto {
    
    @NonNull
    @NotBlank(message = ValidationConstans.NOT_BLANK)
    @Email(message = ValidationConstans.EMAIL_NOT_VALID)
    @Unique(message = ValidationConstans.NOT_UNIQUE)
    String newEmail;
    
    @NonNull
    @NotBlank(message = ValidationConstans.NOT_BLANK)
    @Email(message = ValidationConstans.EMAIL_NOT_VALID)
    @Unique(message = ValidationConstans.NOT_UNIQUE)
    String repeatNewEmail;
    
    @NonNull
    @NotBlank(message = ValidationConstans.NOT_BLANK)
    String password;
}
