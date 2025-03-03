package com.example.Asisgnmentpostgre.Exception;

import com.example.Asisgnmentpostgre.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(EmptyFieldException.class)
    public ApiResponse<String> handleEmptyFieldException(EmptyFieldException ex) {
        return new ApiResponse<>(HttpStatus.NO_CONTENT, ex.getMessage(), null);
    }

    @ExceptionHandler(ActionAlreadyPerformedException.class)
    public ApiResponse<String> handleActionAlreadyPerformedException(ActionAlreadyPerformedException ex) {
        return new ApiResponse<>(HttpStatus.CONFLICT, ex.getMessage(), null);
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ApiResponse<String> handleTypeMismatchException(TypeMismatchException ex) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, "Input Type Error", null);
    }
}
