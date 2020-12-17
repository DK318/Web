package ru.itmo.wp.exception;

public class BadJwtException extends RuntimeException {
    public BadJwtException() {
    }

    public BadJwtException(String message) {
        super(message);
    }

    public BadJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadJwtException(Throwable cause) {
        super(cause);
    }

    public BadJwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
