package com.toolsai.organization_service.exception;

import com.toolsai.organization_service.dto.response.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(e.getMessage());
        errorResponseDTO.setTimestamp(LocalDateTime.now());
        errorResponseDTO.setStatusCode(HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }
}
