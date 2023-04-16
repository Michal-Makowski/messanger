package com.makowski.messenger.dto;

import com.makowski.messenger.validation.Password;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ChangePasswordDto {
    
    @Schema(example = "newEasyPassword1")
    @NonNull
    @Password
    String newPassword;
    
    @Schema(example = "newEasyPassword1")
    @NonNull
    @Password
    String repeatNewPassword;
    
    @Schema(example = "easyPassword1")
    @NonNull
    String oldPassword;
}
