package br.com.registerapi.exception.handler;

import br.com.registerapi.exception.EmailExistingException;
import br.com.registerapi.exception.EmailNotFoundException;
import br.com.registerapi.exception.PasswordInvalidException;
import br.com.registerapi.exception.SessionInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

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

        return new ResponseEntity<>(error, HttpStatus.NO_CONTENT);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        String msg = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
        List<CustomErrorDetails> erros = Arrays.asList(CustomErrorDetails.builder().message(msg).build());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<CustomErrorDetails> erros = criarListaDeErros(ex.getBindingResult());
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<CustomErrorDetails> criarListaDeErros(BindingResult bindingResult) {
        List<CustomErrorDetails> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msg = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            erros.add(CustomErrorDetails.builder().message(msg).build());
        }

        return erros;
    }
}
