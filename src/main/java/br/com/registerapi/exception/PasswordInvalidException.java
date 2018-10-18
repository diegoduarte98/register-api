package br.com.registerapi.exception;

public class PasswordInvalidException extends RuntimeException {

    public PasswordInvalidException() {
        super();
    }
    public PasswordInvalidException(String msg) {
        super(msg);
    }
}