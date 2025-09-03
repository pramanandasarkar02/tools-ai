package com.toolsai.user_service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessageDTO {
    private String message;
    private LocalDateTime timestamp;
    private Integer statusCode;
}
