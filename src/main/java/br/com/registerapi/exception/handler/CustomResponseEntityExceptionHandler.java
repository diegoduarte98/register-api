package br.com.registerapi.exception.handler;

import br.com.registerapi.exception.EmailException;
import br.com.registerapi.exception.UserAndPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private CustomErrorDetails error;

    @ExceptionHandler(UserAndPasswordException.class)
    public ResponseEntity<?> handle(UserAndPasswordException e) {

        error = CustomErrorDetails.builder().message(e.getMessage()).build();

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<?> handle(EmailException e) {

        error = CustomErrorDetails.builder().message(e.getMessage()).build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
