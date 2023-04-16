package com.makowski.messenger.dto;

import com.makowski.messenger.validation.Unique;
import com.makowski.messenger.validation.ValidationConstans;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ChangeEmailDto {
    
    @Schema(example = "newemail@gmail.com")
    @NonNull
    @NotBlank(message = ValidationConstans.NOT_BLANK)
    @Email(message = ValidationConstans.EMAIL_NOT_VALID)
    @Unique(message = ValidationConstans.NOT_UNIQUE)
    String newEmail;
    
    @Schema(example = "newemail@gmail.com")
    @NonNull
    @NotBlank(message = ValidationConstans.NOT_BLANK)
    @Email(message = ValidationConstans.EMAIL_NOT_VALID)
    @Unique(message = ValidationConstans.NOT_UNIQUE)
    String repeatNewEmail;
    
    @Schema(example = "m-muller@gmail.com")
    @NonNull
    @NotBlank(message = ValidationConstans.NOT_BLANK)
    String password;
}
