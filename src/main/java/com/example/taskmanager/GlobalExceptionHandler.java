package com.example.taskmanager;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleTaskNotFound(
        TaskNotFoundException exception) {

            ApiError error = new ApiError(
                401, 
                "Not Found", 
                exception.getMessage()
            ); 

            return ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                   .body(error); 

        }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationError(
           MethodArgumentNotValidException exception) {

        String message = exception.getBindingResult()
                  .getFieldErrors()
                  .stream()
                  .map(error -> error.getDefaultMessage())
                  .findFirst()
                  .orElse("Request Validation failed"); 
        
        ApiError error = new ApiError(
            400, 
            "Bad Request", 
            message
        ); 

        return ResponseEntity 
               .status(HttpStatus.BAD_REQUEST)
               .body(error); 


        }
    
}
