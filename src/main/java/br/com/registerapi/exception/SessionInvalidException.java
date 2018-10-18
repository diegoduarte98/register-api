package br.com.registerapi.exception;

public class SessionInvalidException extends RuntimeException {

    public SessionInvalidException() {
        super();
    }
    public SessionInvalidException(String msg) {
        super(msg);
    }
}