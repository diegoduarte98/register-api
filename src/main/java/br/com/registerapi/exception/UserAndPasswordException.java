package br.com.registerapi.exception;

public class UserAndPasswordException extends RuntimeException {

    public UserAndPasswordException(String msg) {
        super(msg);
    }
}
