package ru.itmo.wp.exception;

public class BadIdException extends RuntimeException {
    public BadIdException() {
    }

    public BadIdException(String message) {
        super(message);
    }

    public BadIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadIdException(Throwable cause) {
        super(cause);
    }

    public BadIdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
