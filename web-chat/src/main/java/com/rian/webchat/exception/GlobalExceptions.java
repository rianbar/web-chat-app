package com.rian.webchat.exception;

import com.rian.webchat.errors.AuthenticationException;
import com.rian.webchat.errors.InvalidCredentialsException;
import com.rian.webchat.errors.UserAlreadyExistsException;
import com.rian.webchat.errors.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;


@ControllerAdvice
public class GlobalExceptions {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> getPayloadValidationViolationException(MethodArgumentNotValidException exp) {
        int code = HttpStatus.BAD_REQUEST.value();
        String status = HttpStatus.BAD_REQUEST.toString();
        String message = Objects.requireNonNull(exp.getFieldError()).getDefaultMessage();

        var response = ResponseErrorTemplate.builder().code(code).status(status).message(message).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> userNotFoundGlobalHandler(UserNotFoundException exp) {
        int code = HttpStatus.NOT_FOUND.value();
        String status = HttpStatus.NOT_FOUND.toString();
        String message = exp.getMessage();

        var response = ResponseErrorTemplate.builder().code(code).status(status).message(message).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> invalidCredentialsGlobalHandler(InvalidCredentialsException exp) {
        int code = HttpStatus.UNAUTHORIZED.value();
        String status = HttpStatus.UNAUTHORIZED.toString();
        String message = exp.getMessage();

        var response = ResponseErrorTemplate.builder().code(code).status(status).message(message).build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> userAlreadyExistsGlobalHandler(UserAlreadyExistsException exp) {
        int code = HttpStatus.CONFLICT.value();
        String status = HttpStatus.CONFLICT.toString();
        String message = exp.getMessage();

        var response = ResponseErrorTemplate.builder().code(code).status(status).message(message).build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> authenticationExceptionGlobalHandler(RuntimeException exp) {
        int code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        String status = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        String message = "internal server error!";

        var response = ResponseErrorTemplate.builder().code(code).status(status).message(message).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
