package com.toolsai.user_service.exception;


import com.toolsai.user_service.dto.ErrorMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
        errorMessageDTO.setMessage(e.getMessage());
        errorMessageDTO.setTimestamp(LocalDateTime.now());
        errorMessageDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(errorMessageDTO);
    }
}
