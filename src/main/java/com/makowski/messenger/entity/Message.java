package com.makowski.messenger.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.makowski.messenger.validation.ValidationConstans;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@Table(name = "message")
public class Message {

    @Schema(readOnly = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(example = "sample text")
    @NonNull
    @Size(min = 1, max = 200, message = ValidationConstans.NOT_SIZE)
    @Column(name = "text", nullable = false)
    private String text;
    
    @Schema(example = "true")
    @Column(name = "permament", nullable = false)
    private boolean permament;

    @Schema(readOnly = true, example = "16-04-2023 20:58:23")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Schema(readOnly = true , example = "23-04-2023 20:58:23")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "expired_at", nullable = true)
    private LocalDateTime expiredAt;

    @Schema(readOnly = true)
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Schema(readOnly = true)
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private Chat chat;

}
