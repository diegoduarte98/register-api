package br.com.registerapi.exception;

public class EmailException extends RuntimeException {

    public EmailException(String msg) {
        super(msg);
    }
}