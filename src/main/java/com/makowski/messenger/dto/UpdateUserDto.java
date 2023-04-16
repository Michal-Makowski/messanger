package com.makowski.messenger.dto;

import com.makowski.messenger.validation.ValidationConstans;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UpdateUserDto {
    
    @Schema(example = "Max")
    @NonNull
    @Size(min = 2, max = 20, message = ValidationConstans.NOT_SIZE)
    @Pattern(regexp = ValidationConstans.PATTERN_AZ, message = ValidationConstans.NO_MATCH_PATTERN_AZ)
    private String firstName;

    @Schema(example = "Muller")
    @NonNull
    @Size(min = 2, max = 20, message = ValidationConstans.NOT_SIZE)
    @Pattern(regexp = ValidationConstans.PATTERN_AZ, message = ValidationConstans.NO_MATCH_PATTERN_AZ)
    private String lastName;
}
