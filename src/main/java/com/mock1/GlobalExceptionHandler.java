package com.mock1;

import com.mock1.auth.components.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> handleEmailExsists(AuthenticationException ex){
        return new ResponseEntity<>("Email already Exsists", HttpStatus.valueOf(401));
    }
    @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleBusiness(IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
    }
}