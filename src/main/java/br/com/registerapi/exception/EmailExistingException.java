package br.com.registerapi.exception;

public class EmailExistingException extends RuntimeException {

    public EmailExistingException() {
        super();
    }

    public EmailExistingException(String msg) {
        super(msg);
    }
}