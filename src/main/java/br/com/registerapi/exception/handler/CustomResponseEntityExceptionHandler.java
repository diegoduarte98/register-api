package br.com.registerapi.exception.handler;

import br.com.registerapi.exception.EmailExistingException;
import br.com.registerapi.exception.EmailNotFoundException;
import br.com.registerapi.exception.PasswordInvalidException;
import br.com.registerapi.exception.SessionInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private CustomErrorDetails error;

    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<?> handle(PasswordInvalidException e) {

        error = CustomErrorDetails.builder().message("Usuário e/ou senha inválidos").build();

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(EmailExistingException.class)
    public ResponseEntity<?> handle(EmailExistingException e) {

        error = CustomErrorDetails.builder().message("E-mail já existente").build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<?> handle(EmailNotFoundException e) {

        error = CustomErrorDetails.builder().message("Usuário e/ou senha inválidos").build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SessionInvalidException.class)
    public ResponseEntity<?> handle(SessionInvalidException e) {

        error = CustomErrorDetails.builder().message("Sessão inválida").build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<?> handle(NoResultException e) {

        error = CustomErrorDetails.builder().message("Nenhum resultado encontrado.").build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
