package com.makowski.messenger.dto;

import com.makowski.messenger.validation.Password;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ChangePasswordDto {
    
    @NonNull
    @Password
    String newPassword;
    @NonNull
    @Password
    String repeatNewPassword;
    @NonNull
    String oldPassword;
}
