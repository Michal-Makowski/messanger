package com.makowski.messenger.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timesptamp;
    @Schema(example = "Error description")
    private List<String> message;

    public ErrorResponse(List<String> message) {
        this.timesptamp = LocalDateTime.now();
        this.message = message;
    }
}
