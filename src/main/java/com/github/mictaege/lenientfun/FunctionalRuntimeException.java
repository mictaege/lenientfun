package com.github.mictaege.lenientfun;

public class FunctionalRuntimeException extends RuntimeException {

    public FunctionalRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FunctionalRuntimeException(final Throwable cause) {
        super(cause);
    }

    public FunctionalRuntimeException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}