package com.makowski.messenger.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.makowski.messenger.validation.Password;
import com.makowski.messenger.validation.Unique;
import com.makowski.messenger.validation.ValidationConstans;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Schema(readOnly = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(example = "MMuller")
    @NonNull
    @Size(min = 2, max = 25, message = ValidationConstans.NOT_SIZE)
    @Pattern(regexp = ValidationConstans.PATTERN_USERNAME, message = ValidationConstans.NO_MATCH_PATTERN_USERNAME)
    @Unique(message = ValidationConstans.NOT_UNIQUE)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Schema(example = "Max")
    @NonNull
    @Size(min = 2, max = 20, message = ValidationConstans.NOT_SIZE)
    @Pattern(regexp = ValidationConstans.PATTERN_AZ, message = ValidationConstans.NO_MATCH_PATTERN_AZ)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Schema(example = "Muller")
    @NonNull
    @Size(min = 2, max = 20, message = ValidationConstans.NOT_SIZE)
    @Pattern(regexp = ValidationConstans.PATTERN_AZ, message = ValidationConstans.NO_MATCH_PATTERN_AZ)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Schema(example = "m-muller@gmail.com")
    @NonNull
    @NotBlank(message = ValidationConstans.NOT_BLANK)
    @Email(message = ValidationConstans.EMAIL_NOT_VALID)
    @Unique(message = ValidationConstans.NOT_UNIQUE)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Schema(example = "easyPassword1", writeOnly = true)
    @NonNull
    @JsonProperty(access = Access.WRITE_ONLY)
    @Password
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages;

    @JsonIgnore
    @ManyToMany(mappedBy = "chatUsers")
    private List<Chat> chats;
}
