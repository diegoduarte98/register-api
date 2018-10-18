package br.com.registerapi.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException() {
        super();
    }
    public EmailNotFoundException(String msg) {
        super(msg);
    }
}