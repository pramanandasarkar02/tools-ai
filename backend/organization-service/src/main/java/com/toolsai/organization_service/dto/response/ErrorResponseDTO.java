package com.toolsai.organization_service.dto.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Data
public class ErrorResponseDTO {
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus statusCode;
}
